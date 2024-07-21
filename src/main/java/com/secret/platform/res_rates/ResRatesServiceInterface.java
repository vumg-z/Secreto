package com.secret.platform.res_rates;

public interface ResRatesServiceInterface {

    /**
     * Get rates based on the provided ResRatesDTO.
     *
     * @param resRatesDTO The data transfer object containing request details.
     * @return ResRatesResponseDTO The response object containing the rates.
     */
    ResRatesResponseDTO getRates(ResRatesDTO resRatesDTO);
}
