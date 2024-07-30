package com.secret.platform.privilege_code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.option_set.OptionSet;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "privilege_codes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;

    private String description;

    private Double uniquePrice;
    private String responsibilityLevel;
    private Double uniqueDropCharge;
    private Double uniqueFuelPrice;
    private Boolean prepaidFuel;
    private Boolean airportFeeExemption;
    private Boolean underageFeeExemption;
    private Boolean otherChargesExemption;
    private Integer uniqueMinimumAge;
    private Boolean applyOptionAutomatically;
    private Double uniqueRentalRate;

    private Boolean premiumAccount;


    @ManyToMany(mappedBy = "privilegeCodes")
    @JsonIgnore
    private List<CorporateContract> corporateContracts;

    @ManyToOne
    @JoinColumn(name = "option_set_id")
    private OptionSet optionSet;

    @Override
    public String toString() {
        return "PrivilegeCode{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", optionSet=" + optionSet +
                '}';
    }
}
