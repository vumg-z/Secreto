package com.secret.platform.options;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionsRateRequest {
    @NotNull(message = "Option code is required")
    private String optionCode;

    @NotNull(message = "Location code is required")
    private String locationCode;

    @NotNull(message = "Currency is required")
    private String currency;

    @NotNull(message = "Primary rate is required")
    private Double primaryRate;

    private Double weeklyRate = 0.0;

    private Double monthlyRate = 0.0;

    private Double xdayRate = 0.0;

    @NotNull(message = "Pricing code is required")
    private String pricingCode;

    @NotNull(message = "Privilege code is required")
    private String privilegeCode;
}
