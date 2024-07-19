package com.secret.platform.rate_product;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "reservas_rental_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rateSet;
    private String product;

    @Temporal(TemporalType.DATE)
    private Date effPkupDate;

    private String effPkupTime;
    private String mustPkupBefore;
    private String comment;
    private String rateType;

    // Page 1 fields
    private Float unused;
    private Float milesMeth;
    private Float week;
    private Float extraWeek;
    private Float freeMilesHour;
    private Integer graceMinutes;
    private Boolean chargeForGrace;
    private Boolean discountable;
    private Boolean editable;
    private Boolean inclCvg1;
    private Boolean inclCvg2;
    private Boolean inclCvg3;
    private Boolean inclCvg4;
    private Integer minDaysForWeek;
    private Integer periodMaxDays;
    private Integer daysPerMonth;
    private String commYn;
    private String commCat;
    private String defltRaType;
    private Boolean corpStd;
    private Boolean searchable;
    private Boolean hide;
    private Boolean noInvoice;
    private Boolean inclOptSet;
    private Boolean addonOptSet;
    private Boolean fpoIncMan;
    private Float oneWayMiles;
    private String altRate1;
    private String currency;
    private Boolean calendarDayCalc;
    private Boolean statusFlag;
    private Float hoursPct;
    private Float yieldPcts;
    private Float tierPcts;
    private Boolean allowLocal;
    private String segmentCode;
    private Float hrsChgToDay;
    private Float hrsXhrsLogic;
    private Boolean milesInIncl;
    private Boolean exemptOptSet;
    private Boolean discOnlyTime;
    private Boolean seamlessEdits;
    private Boolean seamEditRate;
    private Boolean excludeBillto;
    private Boolean ratePrepaid;
    private String paidFreeDay;

    // Page 2 fields
    private String earliestPkupDay;
    private String earliestPkupTime;
    private String latestPkupDay;
    private String latestPkupTime;
    private String earliestReturnDay;
    private String earliestReturnTime;
    private String latestReturnDay;
    private String latestReturnTime;
    private Boolean problemReturnChg;
    private String endMethod;
    private Boolean earlyLateElbo;
    private Boolean packageRate;
    private Float wkndRateMaxDays;
    private Boolean wkndOptions;
    private String startDayMtwfss;
    private String startDayChgTo;
    private Float minDays;
    private Float maxDays;
    private Float intercityChgTo;
    private Integer drvrMinAge;
    private Boolean allowZeroRates;
    private Boolean vlfExempt;
    private Boolean agennExempt;
    private Boolean surchgExempt;
    private Boolean blockFt;
    private String crossReferenceProduct;
    private Boolean seasonal;
    private Boolean multiPeriod;
    private String reportRq;
    private Boolean resOnly;
    private Integer advanceDays;
    private Boolean dropSchedule;
    private Float tier1;
    private Float tier2;
    private Float tier3;
    private Float tier4;
    private Float miles;
    private Float intercity;

    // Page 3 fields
    private Boolean tollOptIn;
    private Boolean fixedTierRate;
    private Integer requoteDays;
    private String discCode;

    // Page 4 fields
    private Float dayRate;
    private Float weekRate;
    private Float monthRate;
    private Float xdayRate;
    private Float hourRate;
    private Float mileRate;
    private Float intercityRate;
    private String prc;
    private Boolean discount;

    // Page 5 fields
    private Float oneWayRate;
    private Float tier1KmRate;
    private Float tier1MRate;
    private Float tier2KmRate;
    private Float tier2MRate;
    private Float tier3KmRate;
    private Float tier3MRate;
    private Float tier4KmRate;
    private Float tier4MRate;

    // Audit fields
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;

    private Float modTime;
    private String modEmpl;
    private String empl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Float time;
}
