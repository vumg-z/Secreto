package com.secret.platform.options;

import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.privilege_code.PrivilegeCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "option_rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionsRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_code", length = 10)
    private String locationCode;

    @Column(name = "option_code", nullable = false, length = 10)
    private String optionCode;

    @ManyToOne
    @JoinColumn(name = "privilege_code_id", nullable = true)
    private PrivilegeCode privilegeCode;

    @ManyToOne
    @JoinColumn(name = "pricing_code_id", nullable = false)
    private PricingCode pricingCode;

    @Column(name = "current_rate")
    private Integer currentRate = 0;

    @Column(name = "currency", nullable = false, length = 10)
    private String currency;

    @Column(name = "effective_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Column(name = "primary_rate", nullable = false)
    private Double primaryRate = 0.0;

    @Column(name = "weekly_rate")
    private Double weeklyRate = 0.0;

    @Column(name = "monthly_rate")
    private Double monthlyRate = 0.0;

    @Column(name = "xday_rate")
    private Double xdayRate = 0.0;

    @Column(name = "hourly_rate")
    private Double hourlyRate = 0.0;

    @Column(name = "transaction_flag", length = 1)
    private String transactionFlag = "0";

    @Column(name = "apply_to_transactions", length = 1)
    private String applyToTransactions = "0";

    @Column(name = "transmittal_mode", length = 1)
    private String transmittalMode = "0";

    @Column(name = "maximum_percentage")
    private Double maximumPercentage = 0.0;

    @Column(name = "coverage", length = 1)
    private String coverage = "0";

    @Column(name = "minimum_age")
    private Integer minimumAge = 0;

    @Column(name = "edit_daily_flag", length = 1)
    private String editDailyFlag = "0";

    @Column(name = "weekly_discount")
    private Double weeklyDiscount = 0.0;

    @Column(name = "maximum_amount")
    private Double maximumAmount = 0.0;

    @Column(name = "premium_percentage")
    private Double premiumPercentage = 0.0;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @Column(name = "report_max_days")
    private Double reportMaxDays = 0.0;

    @Column(name = "tier1_days")
    private Double tier1Days = 0.0;

    @Column(name = "tier2_days")
    private Double tier2Days = 0.0;

    @Column(name = "tier3_days")
    private Double tier3Days = 0.0;

    @Column(name = "tier4_days")
    private Double tier4Days = 0.0;

    @Column(name = "effective_type", length = 1)
    private String effectiveType = "0";

    @Column(name = "response_percentage")
    private Double responsePercentage = 0.0;
}
