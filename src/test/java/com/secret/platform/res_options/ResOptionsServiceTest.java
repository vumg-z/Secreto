package com.secret.platform.res_options;

import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceInterface;
import com.secret.platform.resOptions.ResOptionsDTO;
import com.secret.platform.resOptions.ResOptionsResponseDTO;
import com.secret.platform.resOptions.ResOptionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ResOptionsServiceTest {

    @InjectMocks
    private ResOptionsService resOptionsService;

    @Mock
    private OptionsServiceInterface optionsService;

    private ResOptionsDTO resOptionsDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        resOptionsDTO = new ResOptionsDTO();

    }
}
