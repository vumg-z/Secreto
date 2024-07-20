package com.secret.platform.options;

import com.secret.platform.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;

class OptionsServiceImplTest {

    @InjectMocks
    private OptionsServiceImpl optionsService;

    @Mock
    private OptionsRepository optionsRepository;

    private Options option;

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
                .modifiedTime(10.0)
                .modifiedEmployee("Employee")
                .dayOfWeekPricing("Pricing")
                .useGoldOptSetQtyParts("Parts")
                .estAsgOptPriAgtRls("Rls")
                .optSetCode("Code")
                .build();
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
    void testCreateOption() {
        when(optionsRepository.save(any(Options.class))).thenReturn(option);

        Options result = optionsService.createOption(option);
        assertEquals(option, result);
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
                .modifiedTime(20.0)
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

    // Additional tests for CVG products

    @Test
    void testCreateOption_CVG1() {
        Options cvg1Option = Options.builder()
                .optionCode("CVG1")
                .shortDesc("LDW")
                .longDesc("Loss Damage Waiver")
                .build();

        when(optionsRepository.save(any(Options.class))).thenReturn(cvg1Option);

        Options result = optionsService.createOption(cvg1Option);
        assertEquals("CVG1", result.getOptionCode());
        assertEquals("LDW", result.getShortDesc());
        assertEquals("Loss Damage Waiver", result.getLongDesc());
    }

    @Test
    void testCreateOption_CVG2() {
        Options cvg2Option = Options.builder()
                .optionCode("CVG2")
                .shortDesc("PAI")
                .longDesc("Personal Accident Insurance")
                .build();

        when(optionsRepository.save(any(Options.class))).thenReturn(cvg2Option);

        Options result = optionsService.createOption(cvg2Option);
        assertEquals("CVG2", result.getOptionCode());
        assertEquals("PAI", result.getShortDesc());
        assertEquals("Personal Accident Insurance", result.getLongDesc());
    }

    @Test
    void testCreateOption_CVG3() {
        Options cvg3Option = Options.builder()
                .optionCode("CVG3")
                .shortDesc("SLI")
                .longDesc("Supplemental Liability Insurance")
                .build();

        when(optionsRepository.save(any(Options.class))).thenReturn(cvg3Option);

        Options result = optionsService.createOption(cvg3Option);
        assertEquals("CVG3", result.getOptionCode());
        assertEquals("SLI", result.getShortDesc());
        assertEquals("Supplemental Liability Insurance", result.getLongDesc());
    }

    @Test
    void testCreateOption_CVG4() {
        Options cvg4Option = Options.builder()
                .optionCode("CVG4")
                .shortDesc("Other")
                .longDesc("Other Coverage")
                .build();

        when(optionsRepository.save(any(Options.class))).thenReturn(cvg4Option);

        Options result = optionsService.createOption(cvg4Option);
        assertEquals("CVG4", result.getOptionCode());
        assertEquals("Other", result.getShortDesc());
        assertEquals("Other Coverage", result.getLongDesc());
    }
}
