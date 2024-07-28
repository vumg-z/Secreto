package com.secret.platform.corporate_contract;

import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.rate_product.RateProduct;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "corporate_contracts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CorporateContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractNumber;
    private String brandId;
    private String alternatePlan;
    private String privCodes;
    private Double dailyDiscPct;
    private Double wklyDiscPct;
    private Double raDiscAmt;
    private String documentCode;
    private Double hleIRate;
    private Double hleKRate;
    private String hleRateCodes;
    private Double hleDisc;
    private String discOpt;
    private Boolean gstExempt;
    private String zone;
    private Integer maxDiscDays;

    @ManyToMany
    @JoinTable(
            name = "corporate_contract_privilege_codes",
            joinColumns = @JoinColumn(name = "corporate_contract_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_code_id")
    )
    private List<PrivilegeCode> privilegeCodes;

    /*
    3. RATE PRODUCT

    Enter up to 12 alphanumeric characters that represent an existing CARS+ rate product code. If a rate product does not apply,
    leave this field blank and enter an appropriate discount percent in Fields 5 and 6 or Field 7.

    EXAMPLE: Type AAM2 (RET)
     */

    @ManyToMany
    @JoinTable(
            name = "corporate_contract_rate_products",
            joinColumns = @JoinColumn(name = "corporate_contract_id"),
            inverseJoinColumns = @JoinColumn(name = "rate_product_id")
    )
    private List<RateProduct> rateProducts;

    private String rateProduct;



    @Column(name = "modified_date")
    private LocalDate modifiedDate;
}
