package com.secret.platform.class_code;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.location.Location;
import com.secret.platform.pricing_code.PricingCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClassCodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClassCodeService classCodeService;

    @InjectMocks
    private ClassCodeController classCodeController;

    private ClassCode classCode;
    private Location location;
    private PricingCode pricingCode;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(classCodeController).build();

        location = new Location();
        location.setLocationNumber("GDLMY1");

        pricingCode = new PricingCode();
        pricingCode.setCode("PC1");

        classCode = ClassCode.builder()
                .id(1L)
                .location(location)
                .classCode("A1")
                .description("Compact")
                .pricingCode(pricingCode)
                .build();
    }

    @Test
    public void testGetAllClassCodes() throws Exception {
        when(classCodeService.getAllClassCodes()).thenReturn(Arrays.asList(classCode));

        mockMvc.perform(get("/api/class-codes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].classCode").value("A1"))
                .andExpect(jsonPath("$[0].description").value("Compact"))
                //.andExpect(jsonPath("$[0].location.locationNumber").value("GDLMY1"))
                .andExpect(jsonPath("$[0].pricingCode.code").value("PC1"));

        verify(classCodeService, times(1)).getAllClassCodes();
    }

    @Test
    public void testGetClassCodeById() throws Exception {
        when(classCodeService.getClassCodeById(anyLong())).thenReturn(Optional.of(classCode));

        mockMvc.perform(get("/api/class-codes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.classCode").value("A1"))
                .andExpect(jsonPath("$.description").value("Compact"))
                //.andExpect(jsonPath("$.location.locationNumber").value("GDLMY1"))
                .andExpect(jsonPath("$.pricingCode.code").value("PC1"));

        verify(classCodeService, times(1)).getClassCodeById(1L);
    }

    @Test
    public void testCreateClassCode() throws Exception {
        when(classCodeService.createClassCode(any(ClassCode.class))).thenReturn(classCode);

        mockMvc.perform(post("/api/class-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"location\":{\"locationNumber\":\"GDLMY1\"},\"classCode\":\"A1\",\"description\":\"Compact\",\"pricingCode\":{\"code\":\"PC1\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.classCode").value("A1"))
                .andExpect(jsonPath("$.description").value("Compact"))
                //.andExpect(jsonPath("$.location.locationNumber").value("GDLMY1"))
                .andExpect(jsonPath("$.pricingCode.code").value("PC1"));

        verify(classCodeService, times(1)).createClassCode(any(ClassCode.class));
    }

    @Test
    public void testUpdateClassCode() throws Exception {
        when(classCodeService.updateClassCode(anyLong(), any(ClassCode.class))).thenReturn(classCode);

        mockMvc.perform(put("/api/class-codes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"location\":{\"locationNumber\":\"GDLMY1\"},\"classCode\":\"A1\",\"description\":\"Compact\",\"pricingCode\":{\"code\":\"PC1\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.classCode").value("A1"))
                .andExpect(jsonPath("$.description").value("Compact"))
                //.andExpect(jsonPath("$.location.locationNumber").value("GDLMY1"))
                .andExpect(jsonPath("$.pricingCode.code").value("PC1"));

        verify(classCodeService, times(1)).updateClassCode(anyLong(), any(ClassCode.class));
    }

    @Test
    public void testDeleteClassCode() throws Exception {
        doNothing().when(classCodeService).deleteClassCode(anyLong());

        mockMvc.perform(delete("/api/class-codes/1"))
                .andExpect(status().isNoContent());

        verify(classCodeService, times(1)).deleteClassCode(1L);
    }

    @Test
    public void testDeleteClassCode_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("ClassCode not found for this id :: 1")).when(classCodeService).deleteClassCode(anyLong());

        mockMvc.perform(delete("/api/class-codes/1"))
                .andExpect(status().isNotFound());

        verify(classCodeService, times(1)).deleteClassCode(1L);
    }
}