package com.secret.platform.options_set;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetController;
import com.secret.platform.option_set.OptionSetService;
import com.secret.platform.options.Options;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class OptionSetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OptionSetService optionSetService;

    @InjectMocks
    private OptionSetController optionSetController;

    private OptionSet optionSet;
    private Options option;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(optionSetController).build();

        option = Options.builder()
                .id(1L)
                .optSetCode("PFC03")
                .shortDesc("Option 1")
                .build();

        optionSet = OptionSet.builder()
                .id(1L)
                .code("SET1")
                .effDate(new Date())
                .termDate(new Date())
                .crDateEmpl("Employee1")
                .modDateEmpl("Employee2")
                .options(new ArrayList<>(Arrays.asList(option)))
                .build();
    }

    @Test
    public void testCreateOptionSet() throws Exception {
        when(optionSetService.createOptionSet(any(OptionSet.class))).thenReturn(optionSet);

        mockMvc.perform(post("/api/option-sets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"SET1\",\"effDate\":\"2024-07-22T00:00:00.000+00:00\",\"termDate\":\"2024-07-22T00:00:00.000+00:00\",\"crDateEmpl\":\"Employee1\",\"modDateEmpl\":\"Employee2\",\"options\":[{\"id\":1,\"optSetCode\":\"PFC03\",\"shortDesc\":\"Option 1\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SET1"))
                .andExpect(jsonPath("$.effDate").isNotEmpty())
                .andExpect(jsonPath("$.termDate").isNotEmpty())
                .andExpect(jsonPath("$.crDateEmpl").value("Employee1"))
                .andExpect(jsonPath("$.modDateEmpl").value("Employee2"))
                .andExpect(jsonPath("$.options[0].optSetCode").value("PFC03"));

        verify(optionSetService, times(1)).createOptionSet(any(OptionSet.class));
    }

    @Test
    public void testGetAllOptionSets() throws Exception {
        List<OptionSet> optionSets = Arrays.asList(optionSet);

        when(optionSetService.getAllOptionSets()).thenReturn(optionSets);

        mockMvc.perform(get("/api/option-sets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].code").value("SET1"));

        verify(optionSetService, times(1)).getAllOptionSets();
    }

    @Test
    public void testGetOptionSetById() throws Exception {
        when(optionSetService.getOptionSetById(anyLong())).thenReturn(optionSet);

        mockMvc.perform(get("/api/option-sets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SET1"))
                .andExpect(jsonPath("$.effDate").isNotEmpty())
                .andExpect(jsonPath("$.termDate").isNotEmpty())
                .andExpect(jsonPath("$.crDateEmpl").value("Employee1"))
                .andExpect(jsonPath("$.modDateEmpl").value("Employee2"))
                .andExpect(jsonPath("$.options[0].optSetCode").value("PFC03"));

        verify(optionSetService, times(1)).getOptionSetById(1L);
    }

    @Test
    public void testAddOptionsToOptionSet() throws Exception {
        Options newOption = Options.builder()
                .id(2L)
                .optSetCode("PFC03")
                .shortDesc("Option 2")
                .build();

        OptionSet updatedOptionSet = OptionSet.builder()
                .id(1L)
                .code("SET1")
                .effDate(new Date())
                .termDate(new Date())
                .crDateEmpl("Employee1")
                .modDateEmpl("Employee2")
                .options(new ArrayList<>(Arrays.asList(option, newOption)))
                .build();

        when(optionSetService.getOptionSetById(anyLong())).thenReturn(optionSet);
        when(optionSetService.updateOptionSet(anyLong(), any(OptionSet.class))).thenReturn(updatedOptionSet);

        mockMvc.perform(post("/api/option-sets/1/options")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\":2,\"optSetCode\":\"PFC03\",\"shortDesc\":\"Option 2\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.options.length()").value(2))
                .andExpect(jsonPath("$.options[1].optSetCode").value("PFC03"));

        verify(optionSetService, times(1)).getOptionSetById(1L);
        verify(optionSetService, times(1)).updateOptionSet(anyLong(), any(OptionSet.class));
    }

    @Test
    public void testGetOptionsByOptSetCode() throws Exception {
        List<Options> optionsList = Arrays.asList(option);

        when(optionSetService.getOptionsByOptSetCode(anyString())).thenReturn(optionsList);

        mockMvc.perform(get("/api/option-sets/options?optSetCode=PFC03"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].optSetCode").value("PFC03"))
                .andExpect(jsonPath("$[0].shortDesc").value("Option 1"));

        verify(optionSetService, times(1)).getOptionsByOptSetCode("PFC03");
    }
}
