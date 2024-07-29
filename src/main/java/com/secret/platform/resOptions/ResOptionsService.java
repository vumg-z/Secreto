package com.secret.platform.resOptions;

import org.springframework.stereotype.Service;

@Service
public class ResOptionsService implements ResOptionsServiceInterface {

    @Override
    public ResOptionsResponseDTO getOptions(ResOptionsDTO resOptionsDTO) {
        ResOptionsResponseDTO response = new ResOptionsResponseDTO();
        return response;
    }
}
