package com.secret.platform.rate_set;

import com.secret.platform.rate_product.RateProductResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateSetResponseDTO {
    private Long id;
    private String rateSetCode;
    private String description;
    private List<RateProductResponseDTO> rateProducts;
    private List<LocationResponseDTO> locations;
}
