package com.secret.platform.rate_set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.secret.platform.location.Location;
import com.secret.platform.rate_product.RateProduct;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rate_sets", uniqueConstraints = @UniqueConstraint(columnNames = "rateSetCode"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String rateSetCode;

    private String description;

    @OneToMany(mappedBy = "rateSet")
    @JsonManagedReference
    private List<Location> locations;

    @OneToMany(mappedBy = "rateSet")
    @JsonManagedReference
    private List<RateProduct> rateProducts;
}
