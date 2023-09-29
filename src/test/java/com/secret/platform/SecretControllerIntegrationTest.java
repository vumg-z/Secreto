package com.secret.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.secret.Secret;
import com.secret.platform.secret.SecretController;
import com.secret.platform.secret.SecretService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SecretController.class)
public class SecretControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private SecretService secretService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllSecretsTest() throws Exception {
        when(secretService.getAllSecrets()).thenReturn(Arrays.asList(new Secret(), new Secret()));

        mockMvc.perform(get("/secrets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void getSecretByIdTest() throws Exception {
        Secret secret = new Secret();
        secret.setText("Test Secret");
        when(secretService.getSecretById(1L)).thenReturn(secret);

        mockMvc.perform(get("/secrets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Test Secret"));
    }

    @Test
    public void saveSecretTest() throws Exception {
        Secret secret = new Secret();
        secret.setText("Test Secret");
        when(secretService.saveSecret(any(Secret.class))).thenReturn(secret);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/secrets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secret)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Test Secret"));
    }

    @Test
    public void deleteSecretTest() throws Exception {
        doNothing().when(secretService).deleteSecret(1L);

        mockMvc.perform(delete("/secrets/1"))
                .andExpect(status().isOk());

        verify(secretService, times(1)).deleteSecret(1L);
    }

    @Test
    public void getSecretsByCategoryTest() throws Exception {
        Secret secret1 = new Secret();
        secret1.setText("Secret in Category 1");

        Secret secret2 = new Secret();
        secret2.setText("Another Secret in Category 1");

        when(secretService.getSecretsByCategory(1L)).thenReturn(Arrays.asList(secret1, secret2));

        mockMvc.perform(get("/secrets/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].text").value("Secret in Category 1"))
                .andExpect(jsonPath("$[1].text").value("Another Secret in Category 1"));

        verify(secretService, times(1)).getSecretsByCategory(1L);
    }



}
