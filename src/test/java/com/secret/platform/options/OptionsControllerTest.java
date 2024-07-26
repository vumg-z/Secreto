package com.secret.platform.options;

import com.secret.platform.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class OptionsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OptionsServiceImpl optionsService;

    @InjectMocks
    private OptionsController optionsController;

    private Options option;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(optionsController).build();

        option = Options.builder()
                .id(1L)
                .optionCode("ACCTF")
                .shortDesc("Short Description")
                .longDesc("Long Description")
                .typeFlag("C")
                .glAccount(null)
                .echo("Y")
                .allowQty("Y")
                .insOnly("N")
                .passThru("Y")
                .rptAsRev("Y")
                .webResVisible("Y")
                .dueReport(0.0)
                .duePenalty(0.0)
                .expireDate(new Date())
                .insInvPysCls("Cls")
                .assetByUnit("Unit")
                .effBlkRmvTyp("Type")
                .startDate(new Date())
                .linkedOpt("Opt")
                .restEbdsAuthOpt("Auth")
                .blk1wyMilesSeq(1)
                .modifiedDate(new Date())
                .modifiedTime("10.00")
                .modifiedEmployee("Employee")
                .dayOfWeekPricing("Pricing")
                .useGoldOptSetQtyParts("Parts")
                .estAsgOptPriAgtRls("Rls")
                .optSetCode("Code")
                .build();
    }

    @Test
    public void testCreateOption() throws Exception {
        when(optionsService.createOption(any(Options.class))).thenReturn(option);

        mockMvc.perform(post("/api/options")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"optionCode\":\"ACCTF\",\"shortDesc\":\"Short Description\",\"longDesc\":\"Long Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionCode").value("ACCTF"))
                .andExpect(jsonPath("$.shortDesc").value("Short Description"))
                .andExpect(jsonPath("$.longDesc").value("Long Description"));

        verify(optionsService, times(1)).createOption(any(Options.class));
    }

    @Test
    public void testGetAllOptions() throws Exception {
        List<Options> optionsList = Arrays.asList(option);

        when(optionsService.getAllOptions()).thenReturn(optionsList);

        mockMvc.perform(get("/api/options"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].optionCode").value("ACCTF"));

        verify(optionsService, times(1)).getAllOptions();
    }

    @Test
    public void testGetOptionById() throws Exception {
        when(optionsService.getOptionById(anyLong())).thenReturn(Optional.of(option));

        mockMvc.perform(get("/api/options/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionCode").value("ACCTF"));

        verify(optionsService, times(1)).getOptionById(1L);
    }

    @Test
    public void testGetOptionById_NotFound() throws Exception {
        when(optionsService.getOptionById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/options/1"))
                .andExpect(status().isNotFound());

        verify(optionsService, times(1)).getOptionById(1L);
    }

    @Test
    public void testUpdateOption() throws Exception {
        Options updatedOption = Options.builder()
                .id(1L)
                .optionCode("ACCTF")
                .shortDesc("Updated Short Description")
                .longDesc("Updated Long Description")
                .build();

        when(optionsService.updateOption(anyLong(), any(Options.class))).thenReturn(updatedOption);

        mockMvc.perform(put("/api/options/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"optionCode\":\"ACCTF\",\"shortDesc\":\"Updated Short Description\",\"longDesc\":\"Updated Long Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionCode").value("ACCTF"))
                .andExpect(jsonPath("$.shortDesc").value("Updated Short Description"))
                .andExpect(jsonPath("$.longDesc").value("Updated Long Description"));

        verify(optionsService, times(1)).updateOption(anyLong(), any(Options.class));
    }

    @Test
    public void testDeleteOptionByCode() throws Exception {
        // Mock the service method to do nothing when deleting by optionCode
        doNothing().when(optionsService).deleteOptionByCode(anyString());

        // Perform the delete request and expect a no content status
        mockMvc.perform(delete("/api/options/code/ACCTF"))
                .andExpect(status().isNoContent());

        // Verify that the service method was called once with the expected optionCode
        verify(optionsService, times(1)).deleteOptionByCode("ACCTF");
    }


    @Test
    public void testFindByOptionCode() throws Exception {
        when(optionsService.findByOptionCode(anyString())).thenReturn(option);

        mockMvc.perform(get("/api/options/code/ACCTF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionCode").value("ACCTF"));

        verify(optionsService, times(1)).findByOptionCode("ACCTF");
    }

    @Test
    public void testUpdateOptionSet() throws Exception {
        Options updatedOption = Options.builder()
                .id(1L)
                .optionCode("ACCTF")
                .shortDesc("Short Description")
                .longDesc("Long Description")
                .optSetCode("UpdatedCode")
                .build();

        when(optionsService.updateOption(anyLong(), any(Options.class))).thenReturn(updatedOption);

        mockMvc.perform(put("/api/options/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"optionCode\":\"ACCTF\",\"shortDesc\":\"Short Description\",\"longDesc\":\"Long Description\",\"optionSetCode\":\"UpdatedCode\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionCode").value("ACCTF"))
                .andExpect(jsonPath("$.shortDesc").value("Short Description"))
                .andExpect(jsonPath("$.longDesc").value("Long Description"))
                .andExpect(jsonPath("$.optSetCode").value("UpdatedCode"));

        verify(optionsService, times(1)).updateOption(anyLong(), any(Options.class));
    }

}
