package com.secret.platform.options;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetService;
import com.secret.platform.option_set.OptionSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OptionsServiceImplTest {

    @InjectMocks
    private OptionsServiceImpl optionsService;

    @Mock
    private OptionsRepository optionsRepository;

    @Mock
    private OptionSetService optionSetService;

    @Mock
    private OptionSetRepository optionSetRepository;

    private Options option;
    private OptionSet optionSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
                .modifiedTime("10.0")
                .modifiedEmployee("Employee")
                .dayOfWeekPricing("Pricing")
                .useGoldOptSetQtyParts("Parts")
                .estAsgOptPriAgtRls("Rls")
                .optSetCode("PFC03")
                .build();

        optionSet = OptionSet.builder()
                .id(1L)
                .code("PFC03")
                .effDate(new Date())
                .termDate(null)
                .crDateEmpl("System")
                .modDateEmpl("System")
                .options(new ArrayList<>())
                .build();
    }

    @Test
    void testCreateOption() {
        when(optionsRepository.save(any(Options.class))).thenReturn(option);
        when(optionSetService.findOrCreateOptionSetByCode(anyString())).thenReturn(optionSet);
        when(optionSetRepository.save(any(OptionSet.class))).thenReturn(optionSet);

        Options result = optionsService.createOption(option);

        assertEquals(option, result);
        verify(optionSetService, times(1)).findOrCreateOptionSetByCode("PFC03");
        verify(optionSetRepository, times(1)).save(any(OptionSet.class));
    }

    @Test
    void testGetAllOptions() {
        List<Options> optionsList = new ArrayList<>();
        optionsList.add(option);

        when(optionsRepository.findAll()).thenReturn(optionsList);

        List<Options> result = optionsService.getAllOptions();
        assertEquals(1, result.size());
        assertEquals(option, result.get(0));
    }

    @Test
    void testGetOptionById() {
        when(optionsRepository.findById(1L)).thenReturn(Optional.of(option));

        Optional<Options> result = optionsService.getOptionById(1L);
        assertTrue(result.isPresent());
        assertEquals(option, result.get());
    }

    @Test
    void testGetOptionById_NotFound() {
        when(optionsRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Options> result = optionsService.getOptionById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateOption() {
        when(optionsRepository.findById(1L)).thenReturn(Optional.of(option));
        when(optionsRepository.save(any(Options.class))).thenReturn(option);

        Options updatedOption = Options.builder()
                .id(1L)
                .optionCode("ACCTF")
                .shortDesc("Updated Short Description")
                .longDesc("Updated Long Description")
                .typeFlag("O")
                .glAccount(null)
                .echo("N")
                .allowQty("N")
                .insOnly("Y")
                .passThru("N")
                .rptAsRev("N")
                .webResVisible("N")
                .dueReport(5.0)
                .duePenalty(5.0)
                .expireDate(new Date())
                .insInvPysCls("Updated Cls")
                .assetByUnit("Updated Unit")
                .effBlkRmvTyp("Updated Type")
                .startDate(new Date())
                .linkedOpt("Updated Opt")
                .restEbdsAuthOpt("Updated Auth")
                .blk1wyMilesSeq(2)
                .modifiedDate(new Date())
                .modifiedTime("20.0")
                .modifiedEmployee("Updated Employee")
                .dayOfWeekPricing("Updated Pricing")
                .useGoldOptSetQtyParts("Updated Parts")
                .estAsgOptPriAgtRls("Updated Rls")
                .optSetCode("Updated Code")
                .build();

        Options result = optionsService.updateOption(1L, updatedOption);
        assertEquals(updatedOption.getShortDesc(), result.getShortDesc());
        assertEquals(updatedOption.getLongDesc(), result.getLongDesc());
    }

    @Test
    void testUpdateOption_NotFound() {
        when(optionsRepository.findById(1L)).thenReturn(Optional.empty());

        Options updatedOption = Options.builder().build();

        assertThrows(ResourceNotFoundException.class, () -> {
            optionsService.updateOption(1L, updatedOption);
        });
    }

    @Test
    void testDeleteOption() {
        when(optionsRepository.findById(1L)).thenReturn(Optional.of(option));

        optionsService.deleteOption(1L);

        verify(optionsRepository, times(1)).delete(option);
    }

    @Test
    void testDeleteOption_NotFound() {
        when(optionsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            optionsService.deleteOption(1L);
        });
    }
}
