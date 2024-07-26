package com.secret.platform.options;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.option_set.OptionSetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class OptionsServiceImplTest2 {

    @Mock
    private OptionsRepository optionsRepository;

    @Mock
    private OptionSetRepository optionSetRepository;

    @Mock
    private OptionSetService optionSetService;

    @InjectMocks
    private OptionsServiceImpl optionsService;

    private Options option;
    private OptionSet optionSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        option = Options.builder()
                .id(1L)
                .optionCode("TEST")
                .optSetCode("1")
                .build();

        optionSet = OptionSet.builder()
                .id(1L)
                .code("1")
                .effDate(new Date())
                .termDate(new Date())
                .options(new ArrayList<>())
                .build();
    }

    @Test
    public void testCreateOption() {
        when(optionsRepository.save(any(Options.class))).thenReturn(option);
        when(optionSetService.findOrCreateOptionSetByCode("1")).thenReturn(optionSet);
        when(optionSetRepository.save(any(OptionSet.class))).thenReturn(optionSet);

        Options createdOption = optionsService.createOption(option);

        verify(optionsRepository, times(1)).save(option);
        verify(optionSetService, times(1)).findOrCreateOptionSetByCode("1");
        verify(optionSetRepository, times(1)).save(optionSet);

        assertEquals(option, createdOption);
        assertEquals(1, optionSet.getOptions().size());
        assertEquals(option, optionSet.getOptions().get(0));
    }

    @Test
    public void testUpdateOption() {
        when(optionsRepository.findById(1L)).thenReturn(Optional.of(option));
        when(optionsRepository.save(any(Options.class))).thenReturn(option);

        Options updatedOptionDetails = Options.builder()
                .id(1L)
                .optionCode("UPDATED")
                .optSetCode("1")
                .build();

        Options updatedOption = optionsService.updateOption(1L, updatedOptionDetails);

        verify(optionsRepository, times(1)).findById(1L);
        verify(optionsRepository, times(1)).save(any(Options.class));

        assertEquals(updatedOptionDetails.getOptionCode(), updatedOption.getOptionCode());
    }

    @Test
    public void testDeleteOption() {
        when(optionsRepository.findById(1L)).thenReturn(Optional.of(option));

        optionsService.deleteOption(1L);

        verify(optionsRepository, times(1)).findById(1L);
        verify(optionsRepository, times(1)).delete(option);
    }

    @Test
    public void testFindOptionByOptionCode() {
        when(optionsRepository.findByOptionCode("TEST")).thenReturn(Optional.of(option));

        Options foundOption = optionsService.findByOptionCode("TEST");

        verify(optionsRepository, times(1)).findByOptionCode("TEST");

        assertEquals(option, foundOption);
    }

    @Test
    public void testFindOptionByOptionCode_NotFound() {
        when(optionsRepository.findByOptionCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            optionsService.findByOptionCode("INVALID");
        });
    }

    @Test
    public void testFindByOptSetCode() {
        when(optionsRepository.findByOptSetCode("1")).thenReturn(List.of(option));

        List<Options> optionsList = optionsService.findByOptSetCode("1");

        verify(optionsRepository, times(1)).findByOptSetCode("1");

        assertEquals(1, optionsList.size());
        assertEquals(option, optionsList.get(0));
    }
}
