package com.secret.platform.privilege_code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PrivilegeCodeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PrivilegeCodeController privilegeCodeController;

    @Mock
    private PrivilegeCodeService privilegeCodeService;

    private PrivilegeCode privilegeCode;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(privilegeCodeController).build();

        privilegeCode = PrivilegeCode.builder()
                .id(1L)
                .code("CODE")
                .description("Description")
                .build();
    }

    @Test
    void testGetAllPrivilegeCodes() throws Exception {
        when(privilegeCodeService.getAllPrivilegeCodes()).thenReturn(Collections.singletonList(privilegeCode));

        mockMvc.perform(get("/api/privilege-codes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("CODE"));

        verify(privilegeCodeService, times(1)).getAllPrivilegeCodes();
    }

    @Test
    void testCreatePrivilegeCodeWithOptionSetCodeString() throws Exception {
        // Create a PrivilegeCode object with optionSetCodeString
        privilegeCode.setOptionSetCodeString("OPTION_SET_CODE");

        // Mock the service layer to return this privilegeCode object
        when(privilegeCodeService.createPrivilegeCode(any(PrivilegeCode.class))).thenReturn(privilegeCode);

        // Perform the POST request
        mockMvc.perform(post("/api/privilege-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(privilegeCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE"))
                .andExpect(jsonPath("$.optionSetCodeString").value("OPTION_SET_CODE")); // Validate optionSetCodeString

        // Verify the service interaction
        verify(privilegeCodeService, times(1)).createPrivilegeCode(any(PrivilegeCode.class));
    }


    @Test
    void testGetPrivilegeCodeById() throws Exception {
        when(privilegeCodeService.getPrivilegeCodeById(anyLong())).thenReturn(Optional.of(privilegeCode));

        mockMvc.perform(get("/api/privilege-codes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE"));

        verify(privilegeCodeService, times(1)).getPrivilegeCodeById(1L);
    }

    @Test
    void testGetPrivilegeCodeById_NotFound() throws Exception {
        when(privilegeCodeService.getPrivilegeCodeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/privilege-codes/1"))
                .andExpect(status().isNotFound());

        verify(privilegeCodeService, times(1)).getPrivilegeCodeById(1L);
    }

    @Test
    void testCreatePrivilegeCode() throws Exception {
        when(privilegeCodeService.createPrivilegeCode(any(PrivilegeCode.class))).thenReturn(privilegeCode);

        mockMvc.perform(post("/api/privilege-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                  .content(new ObjectMapper().writeValueAsString(privilegeCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE"));

        verify(privilegeCodeService, times(1)).createPrivilegeCode(any(PrivilegeCode.class));
    }

    @Test
    void testUpdatePrivilegeCode() throws Exception {
        when(privilegeCodeService.updatePrivilegeCode(anyLong(), any(PrivilegeCode.class))).thenReturn(privilegeCode);

        mockMvc.perform(put("/api/privilege-codes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(privilegeCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE"));

        verify(privilegeCodeService, times(1)).updatePrivilegeCode(anyLong(), any(PrivilegeCode.class));
    }

    @Test
    void testUpdatePrivilegeCode_NotFound() throws Exception {
        when(privilegeCodeService.updatePrivilegeCode(anyLong(), any(PrivilegeCode.class))).thenThrow(new ResourceNotFoundException("PrivilegeCode not found with id 1"));

        mockMvc.perform(put("/api/privilege-codes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(privilegeCode)))
                .andExpect(status().isNotFound());

        verify(privilegeCodeService, times(1)).updatePrivilegeCode(anyLong(), any(PrivilegeCode.class));
    }

    @Test
    void testDeletePrivilegeCode() throws Exception {
        doNothing().when(privilegeCodeService).deletePrivilegeCode(anyLong());

        mockMvc.perform(delete("/api/privilege-codes/1"))
                .andExpect(status().isNoContent());

        verify(privilegeCodeService, times(1)).deletePrivilegeCode(1L);
    }

    @Test
    void testDeletePrivilegeCode_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("PrivilegeCode not found with id 1")).when(privilegeCodeService).deletePrivilegeCode(anyLong());

        mockMvc.perform(delete("/api/privilege-codes/1"))
                .andExpect(status().isNotFound());

        verify(privilegeCodeService, times(1)).deletePrivilegeCode(1L);
    }
}
