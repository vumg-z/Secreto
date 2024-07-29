package com.secret.platform.rate_product;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateProductResponseDTO {
    private Long id;
    private String productName;
}
