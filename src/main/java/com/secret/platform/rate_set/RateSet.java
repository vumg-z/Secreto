package com.secret.platform.rate_set;

import com.secret.platform.location.Location;
import com.secret.platform.rate_product.RateProduct;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rate_sets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rateSetCode;
    private String description;

    @OneToMany(mappedBy = "rateSet")
    private List<Location> locations;

    @OneToMany(mappedBy = "rateSet")
    private List<RateProduct> rateProducts;
}
