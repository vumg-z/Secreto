package com.secret.platform.options;

import com.secret.platform.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OptionsServiceTest {

    @InjectMocks
    private OptionsServiceImpl optionsService;

    @Mock
    private OptionsRepository optionsRepository;

    private Options cvg1Option;
    private Options cvg2Option;
    private Options cvg3Option;
    private Options cvg4Option;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cvg1Option = Options.builder()
                .optionCode("CVG1")
                .shortDesc("LDW")
                .longDesc("Loss Damage Waiver")
                .build();

        cvg2Option = Options.builder()
                .optionCode("CVG2")
                .shortDesc("PAI")
                .longDesc("Personal Accident Insurance")
                .build();

        cvg3Option = Options.builder()
                .optionCode("CVG3")
                .shortDesc("SLI")
                .longDesc("Supplemental Liability Insurance")
                .build();

        cvg4Option = Options.builder()
                .optionCode("CVG4")
                .shortDesc("Other")
                .longDesc("Other Coverage")
                .build();
    }

    @Test
    void testCreateOption_CVG1() {
        when(optionsRepository.save(any(Options.class))).thenReturn(cvg1Option);

        Options result = optionsService.createOption(cvg1Option);
        assertEquals("CVG1", result.getOptionCode());
        assertEquals("LDW", result.getShortDesc());
        assertEquals("Loss Damage Waiver", result.getLongDesc());
    }

    @Test
    void testCreateOption_CVG2() {
        when(optionsRepository.save(any(Options.class))).thenReturn(cvg2Option);

        Options result = optionsService.createOption(cvg2Option);
        assertEquals("CVG2", result.getOptionCode());
        assertEquals("PAI", result.getShortDesc());
        assertEquals("Personal Accident Insurance", result.getLongDesc());
    }

    @Test
    void testCreateOption_CVG3() {
        when(optionsRepository.save(any(Options.class))).thenReturn(cvg3Option);

        Options result = optionsService.createOption(cvg3Option);
        assertEquals("CVG3", result.getOptionCode());
        assertEquals("SLI", result.getShortDesc());
        assertEquals("Supplemental Liability Insurance", result.getLongDesc());
    }

    @Test
    void testCreateOption_CVG4() {
        when(optionsRepository.save(any(Options.class))).thenReturn(cvg4Option);

        Options result = optionsService.createOption(cvg4Option);
        assertEquals("CVG4", result.getOptionCode());
        assertEquals("Other", result.getShortDesc());
        assertEquals("Other Coverage", result.getLongDesc());
    }

    @Test
    void testFindOptionByCode() {
        when(optionsRepository.findByOptionCode("CVG1")).thenReturn(Optional.of(cvg1Option));

        Options result = optionsService.findByOptionCode("CVG1");
        assertNotNull(result);
        assertEquals("CVG1", result.getOptionCode());
        assertEquals("Loss Damage Waiver", result.getLongDesc());
    }

    @Test
    void testFindOptionByCode_NotFound() {
        when(optionsRepository.findByOptionCode("CVG5")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            optionsService.findByOptionCode("CVG5");
        });
    }
}
