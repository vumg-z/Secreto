package com.secret.platform.corporate_account;

import com.secret.platform.corporate_contract.CorporateContract;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "corporate_accounts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CorporateAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cdpId;
    private LocalDate startBookDate;
    private LocalDate endBookDate;
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "contract_no", referencedColumnName = "contractNumber", nullable = false)
    private CorporateContract corporateContract;

    private String salesmanCode;
    private Boolean hideRates;
    private String vendorNo;
    private String billTo;
    private String marketSegment;
    private String marketProfile;
    private String salesProject;
    private Boolean webresCCExempt;
    private Boolean webUseAltRates;
    private String cdpCreditCard;
    private LocalDate cdpCCExpireDate;
    private Boolean cdpPINRequired;
    private String city;
    private String zipCode;
    private String paysBy;
    private String tourNo;
    private Boolean ignorePOs;
    private Boolean webCDPRatesOnly;
    private String sourceCode;
    private Boolean webResUseOnly;
    private Boolean monthlyGoal;
    private Boolean exportToNRMA;
    private Boolean searchable;
    private String itCode;
    private Boolean netRateProduct;
    private Boolean removeResHCC;
    private Boolean thirdParty;
    private Boolean allowRAEdit;
    private Boolean preventCCDeposit;
    private Boolean askForDept;
    private Boolean printName;
    private String brandId;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;
}
