package com.secret.platform.rate_product;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservas_rental_rules")
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
    //private Float day;
    private Float week;
    // private Float month;
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

    // Private constructor
    private RateProduct(Builder builder) {
        this.id = builder.id;
        this.rateSet = builder.rateSet;
        this.product = builder.product;
        this.effPkupDate = builder.effPkupDate;
        this.effPkupTime = builder.effPkupTime;
        this.mustPkupBefore = builder.mustPkupBefore;
        this.comment = builder.comment;
        this.rateType = builder.rateType;
        this.unused = builder.unused;
        this.milesMeth = builder.milesMeth;
        this.week = builder.week;
        this.extraWeek = builder.extraWeek;
        this.freeMilesHour = builder.freeMilesHour;
        this.graceMinutes = builder.graceMinutes;
        this.chargeForGrace = builder.chargeForGrace;
        this.discountable = builder.discountable;
        this.editable = builder.editable;
        this.inclCvg1 = builder.inclCvg1;
        this.inclCvg2 = builder.inclCvg2;
        this.inclCvg3 = builder.inclCvg3;
        this.inclCvg4 = builder.inclCvg4;
        this.minDaysForWeek = builder.minDaysForWeek;
        this.periodMaxDays = builder.periodMaxDays;
        this.daysPerMonth = builder.daysPerMonth;
        this.commYn = builder.commYn;
        this.commCat = builder.commCat;
        this.defltRaType = builder.defltRaType;
        this.corpStd = builder.corpStd;
        this.searchable = builder.searchable;
        this.hide = builder.hide;
        this.noInvoice = builder.noInvoice;
        this.inclOptSet = builder.inclOptSet;
        this.addonOptSet = builder.addonOptSet;
        this.fpoIncMan = builder.fpoIncMan;
        this.oneWayMiles = builder.oneWayMiles;
        this.altRate1 = builder.altRate1;
        this.currency = builder.currency;
        this.calendarDayCalc = builder.calendarDayCalc;
        this.statusFlag = builder.statusFlag;
        this.hoursPct = builder.hoursPct;
        this.yieldPcts = builder.yieldPcts;
        this.tierPcts = builder.tierPcts;
        this.allowLocal = builder.allowLocal;
        this.segmentCode = builder.segmentCode;
        this.hrsChgToDay = builder.hrsChgToDay;
        this.hrsXhrsLogic = builder.hrsXhrsLogic;
        this.milesInIncl = builder.milesInIncl;
        this.exemptOptSet = builder.exemptOptSet;
        this.discOnlyTime = builder.discOnlyTime;
        this.seamlessEdits = builder.seamlessEdits;
        this.seamEditRate = builder.seamEditRate;
        this.excludeBillto = builder.excludeBillto;
        this.ratePrepaid = builder.ratePrepaid;
        this.paidFreeDay = builder.paidFreeDay;
        this.earliestPkupDay = builder.earliestPkupDay;
        this.earliestPkupTime = builder.earliestPkupTime;
        this.latestPkupDay = builder.latestPkupDay;
        this.latestPkupTime = builder.latestPkupTime;
        this.earliestReturnDay = builder.earliestReturnDay;
        this.earliestReturnTime = builder.earliestReturnTime;
        this.latestReturnDay = builder.latestReturnDay;
        this.latestReturnTime = builder.latestReturnTime;
        this.problemReturnChg = builder.problemReturnChg;
        this.endMethod = builder.endMethod;
        this.earlyLateElbo = builder.earlyLateElbo;
        this.packageRate = builder.packageRate;
        this.wkndRateMaxDays = builder.wkndRateMaxDays;
        this.wkndOptions = builder.wkndOptions;
        this.startDayMtwfss = builder.startDayMtwfss;
        this.startDayChgTo = builder.startDayChgTo;
        this.minDays = builder.minDays;
        this.maxDays = builder.maxDays;
        this.intercityChgTo = builder.intercityChgTo;
        this.drvrMinAge = builder.drvrMinAge;
        this.allowZeroRates = builder.allowZeroRates;
        this.vlfExempt = builder.vlfExempt;
        this.agennExempt = builder.agennExempt;
        this.surchgExempt = builder.surchgExempt;
        this.blockFt = builder.blockFt;
        this.crossReferenceProduct = builder.crossReferenceProduct;
        this.seasonal = builder.seasonal;
        this.multiPeriod = builder.multiPeriod;
        this.reportRq = builder.reportRq;
        this.resOnly = builder.resOnly;
        this.advanceDays = builder.advanceDays;
        this.dropSchedule = builder.dropSchedule;
        this.tier1 = builder.tier1;
        this.tier2 = builder.tier2;
        this.tier3 = builder.tier3;
        this.tier4 = builder.tier4;
        this.miles = builder.miles;
        this.intercity = builder.intercity;
        this.tollOptIn = builder.tollOptIn;
        this.fixedTierRate = builder.fixedTierRate;
        this.requoteDays = builder.requoteDays;
        this.discCode = builder.discCode;
        this.dayRate = builder.dayRate;
        this.weekRate = builder.weekRate;
        this.monthRate = builder.monthRate;
        this.xdayRate = builder.xdayRate;
        this.hourRate = builder.hourRate;
        this.mileRate = builder.mileRate;
        this.intercityRate = builder.intercityRate;
        this.prc = builder.prc;
        this.discount = builder.discount;
        this.oneWayRate = builder.oneWayRate;
        this.tier1KmRate = builder.tier1KmRate;
        this.tier1MRate = builder.tier1MRate;
        this.tier2KmRate = builder.tier2KmRate;
        this.tier2MRate = builder.tier2MRate;
        this.tier3KmRate = builder.tier3KmRate;
        this.tier3MRate = builder.tier3MRate;
        this.tier4KmRate = builder.tier4KmRate;
        this.tier4MRate = builder.tier4MRate;
        this.modDate = builder.modDate;
        this.modTime = builder.modTime;
        this.modEmpl = builder.modEmpl;
        this.empl = builder.empl;
        this.date = builder.date;
        this.time = builder.time;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String rateSet;
        private String product;
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
        private Date modDate;
        private Float modTime;
        private String modEmpl;
        private String empl;
        private Date date;
        private Float time;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withRateSet(String rateSet) {
            this.rateSet = rateSet;
            return this;
        }

        public Builder withProduct(String product) {
            this.product = product;
            return this;
        }

        public Builder withEffPkupDate(Date effPkupDate) {
            this.effPkupDate = effPkupDate;
            return this;
        }

        public Builder withEffPkupTime(String effPkupTime) {
            this.effPkupTime = effPkupTime;
            return this;
        }

        public Builder withMustPkupBefore(String mustPkupBefore) {
            this.mustPkupBefore = mustPkupBefore;
            return this;
        }

        public Builder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder withRateType(String rateType) {
            this.rateType = rateType;
            return this;
        }

        // Page 1 fields
        public Builder withUnused(Float unused) {
            this.unused = unused;
            return this;
        }

        public Builder withMilesMeth(Float milesMeth) {
            this.milesMeth = milesMeth;
            return this;
        }

        public Builder withWeek(Float week) {
            this.week = week;
            return this;
        }

        public Builder withExtraWeek(Float extraWeek) {
            this.extraWeek = extraWeek;
            return this;
        }

        public Builder withFreeMilesHour(Float freeMilesHour) {
            this.freeMilesHour = freeMilesHour;
            return this;
        }

        public Builder withGraceMinutes(Integer graceMinutes) {
            this.graceMinutes = graceMinutes;
            return this;
        }

        public Builder withChargeForGrace(Boolean chargeForGrace) {
            this.chargeForGrace = chargeForGrace;
            return this;
        }

        public Builder withDiscountable(Boolean discountable) {
            this.discountable = discountable;
            return this;
        }

        public Builder withEditable(Boolean editable) {
            this.editable = editable;
            return this;
        }

        public Builder withInclCvg1(Boolean inclCvg1) {
            this.inclCvg1 = inclCvg1;
            return this;
        }

        public Builder withInclCvg2(Boolean inclCvg2) {
            this.inclCvg2 = inclCvg2;
            return this;
        }

        public Builder withInclCvg3(Boolean inclCvg3) {
            this.inclCvg3 = inclCvg3;
            return this;
        }

        public Builder withInclCvg4(Boolean inclCvg4) {
            this.inclCvg4 = inclCvg4;
            return this;
        }

        public Builder withMinDaysForWeek(Integer minDaysForWeek) {
            this.minDaysForWeek = minDaysForWeek;
            return this;
        }

        public Builder withPeriodMaxDays(Integer periodMaxDays) {
            this.periodMaxDays = periodMaxDays;
            return this;
        }

        public Builder withDaysPerMonth(Integer daysPerMonth) {
            this.daysPerMonth = daysPerMonth;
            return this;
        }

        public Builder withCommYn(String commYn) {
            this.commYn = commYn;
            return this;
        }

        public Builder withCommCat(String commCat) {
            this.commCat = commCat;
            return this;
        }

        public Builder withDefltRaType(String defltRaType) {
            this.defltRaType = defltRaType;
            return this;
        }

        public Builder withCorpStd(Boolean corpStd) {
            this.corpStd = corpStd;
            return this;
        }

        public Builder withSearchable(Boolean searchable) {
            this.searchable = searchable;
            return this;
        }

        public Builder withHide(Boolean hide) {
            this.hide = hide;
            return this;
        }

        public Builder withNoInvoice(Boolean noInvoice) {
            this.noInvoice = noInvoice;
            return this;
        }

        public Builder withInclOptSet(Boolean inclOptSet) {
            this.inclOptSet = inclOptSet;
            return this;
        }

        public Builder withAddonOptSet(Boolean addonOptSet) {
            this.addonOptSet = addonOptSet;
            return this;
        }

        public Builder withFpoIncMan(Boolean fpoIncMan) {
            this.fpoIncMan = fpoIncMan;
            return this;
        }

        public Builder withOneWayMiles(Float oneWayMiles) {
            this.oneWayMiles = oneWayMiles;
            return this;
        }

        public Builder withAltRate1(String altRate1) {
            this.altRate1 = altRate1;
            return this;
        }

        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder withCalendarDayCalc(Boolean calendarDayCalc) {
            this.calendarDayCalc = calendarDayCalc;
            return this;
        }

        public Builder withStatusFlag(Boolean statusFlag) {
            this.statusFlag = statusFlag;
            return this;
        }

        public Builder withHoursPct(Float hoursPct) {
            this.hoursPct = hoursPct;
            return this;
        }

        public Builder withYieldPcts(Float yieldPcts) {
            this.yieldPcts = yieldPcts;
            return this;
        }

        public Builder withTierPcts(Float tierPcts) {
            this.tierPcts = tierPcts;
            return this;
        }

        public Builder withAllowLocal(Boolean allowLocal) {
            this.allowLocal = allowLocal;
            return this;
        }

        public Builder withSegmentCode(String segmentCode) {
            this.segmentCode = segmentCode;
            return this;
        }

        public Builder withHrsChgToDay(Float hrsChgToDay) {
            this.hrsChgToDay = hrsChgToDay;
            return this;
        }

        public Builder withHrsXhrsLogic(Float hrsXhrsLogic) {
            this.hrsXhrsLogic = hrsXhrsLogic;
            return this;
        }

        public Builder withMilesInIncl(Boolean milesInIncl) {
            this.milesInIncl = milesInIncl;
            return this;
        }

        public Builder withExemptOptSet(Boolean exemptOptSet) {
            this.exemptOptSet = exemptOptSet;
            return this;
        }

        public Builder withDiscOnlyTime(Boolean discOnlyTime) {
            this.discOnlyTime = discOnlyTime;
            return this;
        }

        public Builder withSeamlessEdits(Boolean seamlessEdits) {
            this.seamlessEdits = seamlessEdits;
            return this;
        }

        public Builder withSeamEditRate(Boolean seamEditRate) {
            this.seamEditRate = seamEditRate;
            return this;
        }

        public Builder withExcludeBillto(Boolean excludeBillto) {
            this.excludeBillto = excludeBillto;
            return this;
        }

        public Builder withRatePrepaid(Boolean ratePrepaid) {
            this.ratePrepaid = ratePrepaid;
            return this;
        }

        public Builder withPaidFreeDay(String paidFreeDay) {
            this.paidFreeDay = paidFreeDay;
            return this;
        }

        // Page 2 fields
        public Builder withEarliestPkupDay(String earliestPkupDay) {
            this.earliestPkupDay = earliestPkupDay;
            return this;
        }

        public Builder withEarliestPkupTime(String earliestPkupTime) {
            this.earliestPkupTime = earliestPkupTime;
            return this;
        }

        public Builder withLatestPkupDay(String latestPkupDay) {
            this.latestPkupDay = latestPkupDay;
            return this;
        }

        public Builder withLatestPkupTime(String latestPkupTime) {
            this.latestPkupTime = latestPkupTime;
            return this;
        }

        public Builder withEarliestReturnDay(String earliestReturnDay) {
            this.earliestReturnDay = earliestReturnDay;
            return this;
        }

        public Builder withEarliestReturnTime(String earliestReturnTime) {
            this.earliestReturnTime = earliestReturnTime;
            return this;
        }

        public Builder withLatestReturnDay(String latestReturnDay) {
            this.latestReturnDay = latestReturnDay;
            return this;
        }

        public Builder withLatestReturnTime(String latestReturnTime) {
            this.latestReturnTime = latestReturnTime;
            return this;
        }

        public Builder withProblemReturnChg(Boolean problemReturnChg) {
            this.problemReturnChg = problemReturnChg;
            return this;
        }

        public Builder withEndMethod(String endMethod) {
            this.endMethod = endMethod;
            return this;
        }

        public Builder withEarlyLateElbo(Boolean earlyLateElbo) {
            this.earlyLateElbo = earlyLateElbo;
            return this;
        }

        public Builder withPackageRate(Boolean packageRate) {
            this.packageRate = packageRate;
            return this;
        }

        public Builder withWkndRateMaxDays(Float wkndRateMaxDays) {
            this.wkndRateMaxDays = wkndRateMaxDays;
            return this;
        }

        public Builder withWkndOptions(Boolean wkndOptions) {
            this.wkndOptions = wkndOptions;
            return this;
        }

        public Builder withStartDayMtwfss(String startDayMtwfss) {
            this.startDayMtwfss = startDayMtwfss;
            return this;
        }

        public Builder withStartDayChgTo(String startDayChgTo) {
            this.startDayChgTo = startDayChgTo;
            return this;
        }

        public Builder withMinDays(Float minDays) {
            this.minDays = minDays;
            return this;
        }

        public Builder withMaxDays(Float maxDays) {
            this.maxDays = maxDays;
            return this;
        }

        public Builder withIntercityChgTo(Float intercityChgTo) {
            this.intercityChgTo = intercityChgTo;
            return this;
        }

        public Builder withDrvrMinAge(Integer drvrMinAge) {
            this.drvrMinAge = drvrMinAge;
            return this;
        }

        public Builder withAllowZeroRates(Boolean allowZeroRates) {
            this.allowZeroRates = allowZeroRates;
            return this;
        }

        public Builder withVlfExempt(Boolean vlfExempt) {
            this.vlfExempt = vlfExempt;
            return this;
        }

        public Builder withAgennExempt(Boolean agennExempt) {
            this.agennExempt = agennExempt;
            return this;
        }

        public Builder withSurchgExempt(Boolean surchgExempt) {
            this.surchgExempt = surchgExempt;
            return this;
        }

        public Builder withBlockFt(Boolean blockFt) {
            this.blockFt = blockFt;
            return this;
        }

        public Builder withCrossReferenceProduct(String crossReferenceProduct) {
            this.crossReferenceProduct = crossReferenceProduct;
            return this;
        }

        public Builder withSeasonal(Boolean seasonal) {
            this.seasonal = seasonal;
            return this;
        }

        public Builder withMultiPeriod(Boolean multiPeriod) {
            this.multiPeriod = multiPeriod;
            return this;
        }

        public Builder withReportRq(String reportRq) {
            this.reportRq = reportRq;
            return this;
        }

        public Builder withResOnly(Boolean resOnly) {
            this.resOnly = resOnly;
            return this;
        }

        public Builder withAdvanceDays(Integer advanceDays) {
            this.advanceDays = advanceDays;
            return this;
        }

        public Builder withDropSchedule(Boolean dropSchedule) {
            this.dropSchedule = dropSchedule;
            return this;
        }

        public Builder withTier1(Float tier1) {
            this.tier1 = tier1;
            return this;
        }

        public Builder withTier2(Float tier2) {
            this.tier2 = tier2;
            return this;
        }

        public Builder withTier3(Float tier3) {
            this.tier3 = tier3;
            return this;
        }

        public Builder withTier4(Float tier4) {
            this.tier4 = tier4;
            return this;
        }

        public Builder withMiles(Float miles) {
            this.miles = miles;
            return this;
        }

        public Builder withIntercity(Float intercity) {
            this.intercity = intercity;
            return this;
        }

        // Page 3 fields
        public Builder withTollOptIn(Boolean tollOptIn) {
            this.tollOptIn = tollOptIn;
            return this;
        }

        public Builder withFixedTierRate(Boolean fixedTierRate) {
            this.fixedTierRate = fixedTierRate;
            return this;
        }

        public Builder withRequoteDays(Integer requoteDays) {
            this.requoteDays = requoteDays;
            return this;
        }

        public Builder withDiscCode(String discCode) {
            this.discCode = discCode;
            return this;
        }

        // Page 4 fields
        public Builder withDayRate(Float dayRate) {
            this.dayRate = dayRate;
            return this;
        }

        public Builder withWeekRate(Float weekRate) {
            this.weekRate = weekRate;
            return this;
        }

        public Builder withMonthRate(Float monthRate) {
            this.monthRate = monthRate;
            return this;
        }

        public Builder withXdayRate(Float xdayRate) {
            this.xdayRate = xdayRate;
            return this;
        }

        public Builder withHourRate(Float hourRate) {
            this.hourRate = hourRate;
            return this;
        }

        public Builder withMileRate(Float mileRate) {
            this.mileRate = mileRate;
            return this;
        }

        public Builder withIntercityRate(Float intercityRate) {
            this.intercityRate = intercityRate;
            return this;
        }

        public Builder withPrc(String prc) {
            this.prc = prc;
            return this;
        }

        public Builder withDiscount(Boolean discount) {
            this.discount = discount;
            return this;
        }

        // Page 5 fields
        public Builder withOneWayRate(Float oneWayRate) {
            this.oneWayRate = oneWayRate;
            return this;
        }

        public Builder withTier1KmRate(Float tier1KmRate) {
            this.tier1KmRate = tier1KmRate;
            return this;
        }

        public Builder withTier1MRate(Float tier1MRate) {
            this.tier1MRate = tier1MRate;
            return this;
        }

        public Builder withTier2KmRate(Float tier2KmRate) {
            this.tier2KmRate = tier2KmRate;
            return this;
        }

        public Builder withTier2MRate(Float tier2MRate) {
            this.tier2MRate = tier2MRate;
            return this;
        }

        public Builder withTier3KmRate(Float tier3KmRate) {
            this.tier3KmRate = tier3KmRate;
            return this;
        }

        public Builder withTier3MRate(Float tier3MRate) {
            this.tier3MRate = tier3MRate;
            return this;
        }

        public Builder withTier4KmRate(Float tier4KmRate) {
            this.tier4KmRate = tier4KmRate;
            return this;
        }

        public Builder withTier4MRate(Float tier4MRate) {
            this.tier4MRate = tier4MRate;
            return this;
        }

        // Audit fields
        public Builder withModDate(Date modDate) {
            this.modDate = modDate;
            return this;
        }

        public Builder withModTime(Float modTime) {
            this.modTime = modTime;
            return this;
        }

        public Builder withModEmpl(String modEmpl) {
            this.modEmpl = modEmpl;
            return this;
        }

        public Builder withEmpl(String empl) {
            this.empl = empl;
            return this;
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder withTime(Float time) {
            this.time = time;
            return this;
        }

        public RateProduct build() {
            return new RateProduct(this);
        }
    }

    public String getRateSet() {
        return rateSet;
    }

    public void setRateSet(String rateSet) {
        this.rateSet = rateSet;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Date getEffPkupDate() {
        return effPkupDate;
    }

    public void setEffPkupDate(Date effPkupDate) {
        this.effPkupDate = effPkupDate;
    }

    public String getEffPkupTime() {
        return effPkupTime;
    }

    public void setEffPkupTime(String effPkupTime) {
        this.effPkupTime = effPkupTime;
    }

    public String getMustPkupBefore() {
        return mustPkupBefore;
    }

    public void setMustPkupBefore(String mustPkupBefore) {
        this.mustPkupBefore = mustPkupBefore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public Float getUnused() {
        return unused;
    }

    public void setUnused(Float unused) {
        this.unused = unused;
    }

    public Float getMilesMeth() {
        return milesMeth;
    }

    public void setMilesMeth(Float milesMeth) {
        this.milesMeth = milesMeth;
    }

    public Float getWeek() {
        return week;
    }

    public void setWeek(Float week) {
        this.week = week;
    }

    public Float getExtraWeek() {
        return extraWeek;
    }

    public void setExtraWeek(Float extraWeek) {
        this.extraWeek = extraWeek;
    }

    public Float getFreeMilesHour() {
        return freeMilesHour;
    }

    public void setFreeMilesHour(Float freeMilesHour) {
        this.freeMilesHour = freeMilesHour;
    }

    public Integer getGraceMinutes() {
        return graceMinutes;
    }

    public void setGraceMinutes(Integer graceMinutes) {
        this.graceMinutes = graceMinutes;
    }

    public Boolean getChargeForGrace() {
        return chargeForGrace;
    }

    public void setChargeForGrace(Boolean chargeForGrace) {
        this.chargeForGrace = chargeForGrace;
    }

    public Boolean getDiscountable() {
        return discountable;
    }

    public void setDiscountable(Boolean discountable) {
        this.discountable = discountable;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getInclCvg1() {
        return inclCvg1;
    }

    public void setInclCvg1(Boolean inclCvg1) {
        this.inclCvg1 = inclCvg1;
    }

    public Boolean getInclCvg2() {
        return inclCvg2;
    }

    public void setInclCvg2(Boolean inclCvg2) {
        this.inclCvg2 = inclCvg2;
    }

    public Boolean getInclCvg3() {
        return inclCvg3;
    }

    public void setInclCvg3(Boolean inclCvg3) {
        this.inclCvg3 = inclCvg3;
    }

    public Boolean getInclCvg4() {
        return inclCvg4;
    }

    public void setInclCvg4(Boolean inclCvg4) {
        this.inclCvg4 = inclCvg4;
    }

    public Integer getMinDaysForWeek() {
        return minDaysForWeek;
    }

    public void setMinDaysForWeek(Integer minDaysForWeek) {
        this.minDaysForWeek = minDaysForWeek;
    }

    public Integer getPeriodMaxDays() {
        return periodMaxDays;
    }

    public void setPeriodMaxDays(Integer periodMaxDays) {
        this.periodMaxDays = periodMaxDays;
    }

    public Integer getDaysPerMonth() {
        return daysPerMonth;
    }

    public void setDaysPerMonth(Integer daysPerMonth) {
        this.daysPerMonth = daysPerMonth;
    }

    public String getCommYn() {
        return commYn;
    }

    public void setCommYn(String commYn) {
        this.commYn = commYn;
    }

    public String getCommCat() {
        return commCat;
    }

    public void setCommCat(String commCat) {
        this.commCat = commCat;
    }

    public String getDefltRaType() {
        return defltRaType;
    }

    public void setDefltRaType(String defltRaType) {
        this.defltRaType = defltRaType;
    }

    public Boolean getCorpStd() {
        return corpStd;
    }

    public void setCorpStd(Boolean corpStd) {
        this.corpStd = corpStd;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(Boolean noInvoice) {
        this.noInvoice = noInvoice;
    }

    public Boolean getInclOptSet() {
        return inclOptSet;
    }

    public void setInclOptSet(Boolean inclOptSet) {
        this.inclOptSet = inclOptSet;
    }

    public Boolean getAddonOptSet() {
        return addonOptSet;
    }

    public void setAddonOptSet(Boolean addonOptSet) {
        this.addonOptSet = addonOptSet;
    }

    public Boolean getFpoIncMan() {
        return fpoIncMan;
    }

    public void setFpoIncMan(Boolean fpoIncMan) {
        this.fpoIncMan = fpoIncMan;
    }

    public Float getOneWayMiles() {
        return oneWayMiles;
    }

    public void setOneWayMiles(Float oneWayMiles) {
        this.oneWayMiles = oneWayMiles;
    }

    public String getAltRate1() {
        return altRate1;
    }

    public void setAltRate1(String altRate1) {
        this.altRate1 = altRate1;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getCalendarDayCalc() {
        return calendarDayCalc;
    }

    public void setCalendarDayCalc(Boolean calendarDayCalc) {
        this.calendarDayCalc = calendarDayCalc;
    }

    public Boolean getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Boolean statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Float getHoursPct() {
        return hoursPct;
    }

    public void setHoursPct(Float hoursPct) {
        this.hoursPct = hoursPct;
    }

    public Float getYieldPcts() {
        return yieldPcts;
    }

    public void setYieldPcts(Float yieldPcts) {
        this.yieldPcts = yieldPcts;
    }

    public Float getTierPcts() {
        return tierPcts;
    }

    public void setTierPcts(Float tierPcts) {
        this.tierPcts = tierPcts;
    }

    public Boolean getAllowLocal() {
        return allowLocal;
    }

    public void setAllowLocal(Boolean allowLocal) {
        this.allowLocal = allowLocal;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public Float getHrsChgToDay() {
        return hrsChgToDay;
    }

    public void setHrsChgToDay(Float hrsChgToDay) {
        this.hrsChgToDay = hrsChgToDay;
    }

    public Float getHrsXhrsLogic() {
        return hrsXhrsLogic;
    }

    public void setHrsXhrsLogic(Float hrsXhrsLogic) {
        this.hrsXhrsLogic = hrsXhrsLogic;
    }

    public Boolean getMilesInIncl() {
        return milesInIncl;
    }

    public void setMilesInIncl(Boolean milesInIncl) {
        this.milesInIncl = milesInIncl;
    }

    public Boolean getExemptOptSet() {
        return exemptOptSet;
    }

    public void setExemptOptSet(Boolean exemptOptSet) {
        this.exemptOptSet = exemptOptSet;
    }

    public Boolean getDiscOnlyTime() {
        return discOnlyTime;
    }

    public void setDiscOnlyTime(Boolean discOnlyTime) {
        this.discOnlyTime = discOnlyTime;
    }

    public Boolean getSeamlessEdits() {
        return seamlessEdits;
    }

    public void setSeamlessEdits(Boolean seamlessEdits) {
        this.seamlessEdits = seamlessEdits;
    }

    public Boolean getSeamEditRate() {
        return seamEditRate;
    }

    public void setSeamEditRate(Boolean seamEditRate) {
        this.seamEditRate = seamEditRate;
    }

    public Boolean getExcludeBillto() {
        return excludeBillto;
    }

    public void setExcludeBillto(Boolean excludeBillto) {
        this.excludeBillto = excludeBillto;
    }

    public Boolean getRatePrepaid() {
        return ratePrepaid;
    }

    public void setRatePrepaid(Boolean ratePrepaid) {
        this.ratePrepaid = ratePrepaid;
    }

    public String getPaidFreeDay() {
        return paidFreeDay;
    }

    public void setPaidFreeDay(String paidFreeDay) {
        this.paidFreeDay = paidFreeDay;
    }

    public String getEarliestPkupDay() {
        return earliestPkupDay;
    }

    public void setEarliestPkupDay(String earliestPkupDay) {
        this.earliestPkupDay = earliestPkupDay;
    }

    public String getEarliestPkupTime() {
        return earliestPkupTime;
    }

    public void setEarliestPkupTime(String earliestPkupTime) {
        this.earliestPkupTime = earliestPkupTime;
    }

    public String getLatestPkupDay() {
        return latestPkupDay;
    }

    public void setLatestPkupDay(String latestPkupDay) {
        this.latestPkupDay = latestPkupDay;
    }

    public String getLatestPkupTime() {
        return latestPkupTime;
    }

    public void setLatestPkupTime(String latestPkupTime) {
        this.latestPkupTime = latestPkupTime;
    }

    public String getEarliestReturnDay() {
        return earliestReturnDay;
    }

    public void setEarliestReturnDay(String earliestReturnDay) {
        this.earliestReturnDay = earliestReturnDay;
    }

    public String getEarliestReturnTime() {
        return earliestReturnTime;
    }

    public void setEarliestReturnTime(String earliestReturnTime) {
        this.earliestReturnTime = earliestReturnTime;
    }

    public String getLatestReturnDay() {
        return latestReturnDay;
    }

    public void setLatestReturnDay(String latestReturnDay) {
        this.latestReturnDay = latestReturnDay;
    }

    public String getLatestReturnTime() {
        return latestReturnTime;
    }

    public void setLatestReturnTime(String latestReturnTime) {
        this.latestReturnTime = latestReturnTime;
    }

    public Boolean getProblemReturnChg() {
        return problemReturnChg;
    }

    public void setProblemReturnChg(Boolean problemReturnChg) {
        this.problemReturnChg = problemReturnChg;
    }

    public String getEndMethod() {
        return endMethod;
    }

    public void setEndMethod(String endMethod) {
        this.endMethod = endMethod;
    }

    public Boolean getEarlyLateElbo() {
        return earlyLateElbo;
    }

    public void setEarlyLateElbo(Boolean earlyLateElbo) {
        this.earlyLateElbo = earlyLateElbo;
    }

    public Boolean getPackageRate() {
        return packageRate;
    }

    public void setPackageRate(Boolean packageRate) {
        this.packageRate = packageRate;
    }

    public Float getWkndRateMaxDays() {
        return wkndRateMaxDays;
    }

    public void setWkndRateMaxDays(Float wkndRateMaxDays) {
        this.wkndRateMaxDays = wkndRateMaxDays;
    }

    public Boolean getWkndOptions() {
        return wkndOptions;
    }

    public void setWkndOptions(Boolean wkndOptions) {
        this.wkndOptions = wkndOptions;
    }

    public String getStartDayMtwfss() {
        return startDayMtwfss;
    }

    public void setStartDayMtwfss(String startDayMtwfss) {
        this.startDayMtwfss = startDayMtwfss;
    }

    public String getStartDayChgTo() {
        return startDayChgTo;
    }

    public void setStartDayChgTo(String startDayChgTo) {
        this.startDayChgTo = startDayChgTo;
    }

    public Float getMinDays() {
        return minDays;
    }

    public void setMinDays(Float minDays) {
        this.minDays = minDays;
    }

    public Float getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Float maxDays) {
        this.maxDays = maxDays;
    }

    public Float getIntercityChgTo() {
        return intercityChgTo;
    }

    public void setIntercityChgTo(Float intercityChgTo) {
        this.intercityChgTo = intercityChgTo;
    }

    public Integer getDrvrMinAge() {
        return drvrMinAge;
    }

    public void setDrvrMinAge(Integer drvrMinAge) {
        this.drvrMinAge = drvrMinAge;
    }

    public Boolean getAllowZeroRates() {
        return allowZeroRates;
    }

    public void setAllowZeroRates(Boolean allowZeroRates) {
        this.allowZeroRates = allowZeroRates;
    }

    public Boolean getVlfExempt() {
        return vlfExempt;
    }

    public void setVlfExempt(Boolean vlfExempt) {
        this.vlfExempt = vlfExempt;
    }

    public Boolean getAgennExempt() {
        return agennExempt;
    }

    public void setAgennExempt(Boolean agennExempt) {
        this.agennExempt = agennExempt;
    }

    public Boolean getSurchgExempt() {
        return surchgExempt;
    }

    public void setSurchgExempt(Boolean surchgExempt) {
        this.surchgExempt = surchgExempt;
    }

    public Boolean getBlockFt() {
        return blockFt;
    }

    public void setBlockFt(Boolean blockFt) {
        this.blockFt = blockFt;
    }

    public String getCrossReferenceProduct() {
        return crossReferenceProduct;
    }

    public void setCrossReferenceProduct(String crossReferenceProduct) {
        this.crossReferenceProduct = crossReferenceProduct;
    }

    public Boolean getSeasonal() {
        return seasonal;
    }

    public void setSeasonal(Boolean seasonal) {
        this.seasonal = seasonal;
    }

    public Boolean getMultiPeriod() {
        return multiPeriod;
    }

    public void setMultiPeriod(Boolean multiPeriod) {
        this.multiPeriod = multiPeriod;
    }

    public String getReportRq() {
        return reportRq;
    }

    public void setReportRq(String reportRq) {
        this.reportRq = reportRq;
    }

    public Boolean getResOnly() {
        return resOnly;
    }

    public void setResOnly(Boolean resOnly) {
        this.resOnly = resOnly;
    }

    public Integer getAdvanceDays() {
        return advanceDays;
    }

    public void setAdvanceDays(Integer advanceDays) {
        this.advanceDays = advanceDays;
    }

    public Boolean getDropSchedule() {
        return dropSchedule;
    }

    public void setDropSchedule(Boolean dropSchedule) {
        this.dropSchedule = dropSchedule;
    }

    public Float getTier1() {
        return tier1;
    }

    public void setTier1(Float tier1) {
        this.tier1 = tier1;
    }

    public Float getTier2() {
        return tier2;
    }

    public void setTier2(Float tier2) {
        this.tier2 = tier2;
    }

    public Float getTier3() {
        return tier3;
    }

    public void setTier3(Float tier3) {
        this.tier3 = tier3;
    }

    public Float getTier4() {
        return tier4;
    }

    public void setTier4(Float tier4) {
        this.tier4 = tier4;
    }

    public Float getMiles() {
        return miles;
    }

    public void setMiles(Float miles) {
        this.miles = miles;
    }

    public Float getIntercity() {
        return intercity;
    }

    public void setIntercity(Float intercity) {
        this.intercity = intercity;
    }

    public Boolean getTollOptIn() {
        return tollOptIn;
    }

    public void setTollOptIn(Boolean tollOptIn) {
        this.tollOptIn = tollOptIn;
    }

    public Boolean getFixedTierRate() {
        return fixedTierRate;
    }

    public void setFixedTierRate(Boolean fixedTierRate) {
        this.fixedTierRate = fixedTierRate;
    }

    public Integer getRequoteDays() {
        return requoteDays;
    }

    public void setRequoteDays(Integer requoteDays) {
        this.requoteDays = requoteDays;
    }

    public String getDiscCode() {
        return discCode;
    }

    public void setDiscCode(String discCode) {
        this.discCode = discCode;
    }

    public Float getDayRate() {
        return dayRate;
    }

    public void setDayRate(Float dayRate) {
        this.dayRate = dayRate;
    }

    public Float getWeekRate() {
        return weekRate;
    }

    public void setWeekRate(Float weekRate) {
        this.weekRate = weekRate;
    }

    public Float getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(Float monthRate) {
        this.monthRate = monthRate;
    }

    public Float getXdayRate() {
        return xdayRate;
    }

    public void setXdayRate(Float xdayRate) {
        this.xdayRate = xdayRate;
    }

    public Float getHourRate() {
        return hourRate;
    }

    public void setHourRate(Float hourRate) {
        this.hourRate = hourRate;
    }

    public Float getMileRate() {
        return mileRate;
    }

    public void setMileRate(Float mileRate) {
        this.mileRate = mileRate;
    }

    public Float getIntercityRate() {
        return intercityRate;
    }

    public void setIntercityRate(Float intercityRate) {
        this.intercityRate = intercityRate;
    }

    public String getPrc() {
        return prc;
    }

    public void setPrc(String prc) {
        this.prc = prc;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public Float getOneWayRate() {
        return oneWayRate;
    }

    public void setOneWayRate(Float oneWayRate) {
        this.oneWayRate = oneWayRate;
    }

    public Float getTier1KmRate() {
        return tier1KmRate;
    }

    public void setTier1KmRate(Float tier1KmRate) {
        this.tier1KmRate = tier1KmRate;
    }

    public Float getTier1MRate() {
        return tier1MRate;
    }

    public void setTier1MRate(Float tier1MRate) {
        this.tier1MRate = tier1MRate;
    }

    public Float getTier2KmRate() {
        return tier2KmRate;
    }

    public void setTier2KmRate(Float tier2KmRate) {
        this.tier2KmRate = tier2KmRate;
    }

    public Float getTier2MRate() {
        return tier2MRate;
    }

    public void setTier2MRate(Float tier2MRate) {
        this.tier2MRate = tier2MRate;
    }

    public Float getTier3KmRate() {
        return tier3KmRate;
    }

    public void setTier3KmRate(Float tier3KmRate) {
        this.tier3KmRate = tier3KmRate;
    }

    public Float getTier3MRate() {
        return tier3MRate;
    }

    public void setTier3MRate(Float tier3MRate) {
        this.tier3MRate = tier3MRate;
    }

    public Float getTier4KmRate() {
        return tier4KmRate;
    }

    public void setTier4KmRate(Float tier4KmRate) {
        this.tier4KmRate = tier4KmRate;
    }

    public Float getTier4MRate() {
        return tier4MRate;
    }

    public void setTier4MRate(Float tier4MRate) {
        this.tier4MRate = tier4MRate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public Float getModTime() {
        return modTime;
    }

    public void setModTime(Float modTime) {
        this.modTime = modTime;
    }

    public String getModEmpl() {
        return modEmpl;
    }

    public void setModEmpl(String modEmpl) {
        this.modEmpl = modEmpl;
    }

    public String getEmpl() {
        return empl;
    }

    public void setEmpl(String empl) {
        this.empl = empl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }

}
