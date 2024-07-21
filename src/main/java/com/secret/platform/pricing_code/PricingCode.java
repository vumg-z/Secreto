package com.secret.platform.pricing_code;

import com.secret.platform.class_code.ClassCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.Set;

@Entity
@Table(name = "pricing_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(name = "ldw_rate", nullable = false)
    private Double ldwRate;

    @Column(name = "no_ldw_resp")
    private Double noLdwResp;

    @Column(name = "no_ldw_age_1")
    private Integer noLdwAge1;

    @Column(name = "no_ldw_resp_1")
    private Double noLdwResp1;

    @Column(name = "no_ldw_age_2")
    private Integer noLdwAge2;

    @Column(name = "no_ldw_resp_2")
    private Double noLdwResp2;

    @Column(name = "incl_ldw_resp")
    private Double inclLdwResp;

    @Column(name = "cvg2_value")
    private Double cvg2Value;

    @Column(name = "cvg3_value")
    private Double cvg3Value;

    @Column(name = "cvg4_value")
    private Double cvg4Value;

    @OneToMany(mappedBy = "pricingCode")
    private Set<ClassCode> classCodes;
}
