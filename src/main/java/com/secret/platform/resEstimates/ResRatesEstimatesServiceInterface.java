package com.secret.platform.resEstimates;

public interface ResRatesEstimatesServiceInterface {

    ResEstimatesResponseDTO getEstimates(ResEstimatesDTO resEstimatesDTO);

    ResEstimatesResponseDTO getEstimates(ResEstimatesDTO resEstimatesDTO, String currency);
}
