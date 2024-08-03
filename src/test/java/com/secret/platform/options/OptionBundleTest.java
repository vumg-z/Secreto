package com.secret.platform.options;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OptionsController.class)
public class OptionBundleTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OptionsServiceImpl optionsService;

    private Options option;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        option = Options.builder()
                .optionCode("OPT123")
                .shortDesc("Short description")
                .longDesc("Long description")
                .bundle(true)
                .build();
    }

    @Test
    public void testCreateOption() throws Exception {
        when(optionsService.createOption(any(Options.class))).thenReturn(option);

        mockMvc.perform(post("/api/options")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(option)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionCode").value("OPT123"))
                .andExpect(jsonPath("$.shortDesc").value("Short description"))
                .andExpect(jsonPath("$.longDesc").value("Long description"))
                .andExpect(jsonPath("$.bundle").value(true));
    }
}
