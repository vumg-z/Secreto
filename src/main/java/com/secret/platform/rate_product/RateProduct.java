package com.secret.platform.rate_product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.secret.platform.class_code.ClassCode;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.options.Options;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.type_code.ValidTypeCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "rate_set_id")
    @JsonBackReference
    private RateSet rateSet;

    @Transient // This field will not be persisted in the database
    @JsonProperty("rateSetCode")
    public String getRateSetCode() {
        return rateSet != null ? rateSet.getRateSetCode() : null;
    }

    private String product;

    @Temporal(TemporalType.DATE)
    private Date effPkupDate;

    private String effPkupTime;
    private String mustPkupBefore;
    private String comment;
    private String rateType;



    /*
        INCLUDE CVG1 & CVG2, INCLUDE CVG3 & CVG4
    These Yes/No fields indicate if specific coverages are included at no charge with a rate product:

    CVG1 (LDW): Y means LDW is included for free.
    CVG2 - CVG4: Reserved for additional coverages (e.g., PAI). Y means these are included for free.

    Key Points
    Included Coverages:

    Y: Coverage is included at no charge, blocking rental agents from selling it separately.
    N: Coverage is not included in the Time charges.

    Revenue Handling:

    Only CVG1 (LDW) can have a percentage of the rate allocated in the Accounting DBR.
    CVG2-CVG4 do not extract revenue from Time charges but are estimated in the Net Time and Mileage Report.
    Considerations:

    Minimum/maximum days rules may not automatically update included coverages.
    Not used for net rates; use Option Sets for net rates.

     */

    @ManyToMany
    @JoinTable(
            name = "rate_product_options",
            joinColumns = @JoinColumn(name = "rate_product_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<Options> includedOptions = new ArrayList<>();


    private Boolean inclCvg1;
    private Boolean inclCvg2;
    private Boolean inclCvg3;
    private Boolean inclCvg4;

    // Page 1 fields
    private Float unused = 0.0f;
    private Float milesMeth = 0.0f;
    private Float week = 0.0f;
    private Float extraWeek = 0.0f;
    private Float freeMilesHour = 0.0f;


    /*
        21. GRACE MINUTES
        Enter up to 3 numeric characters to indicate in minutes the grace period allowed. If a grace period is not offered on this product, skip this field.
        CARS+ will apply the grace minutes at the end of any 24 hour period or multiple thereof from the time of check-out. A grace period allows the renter to return the vehicle late without having to pay for additional rental charges.
        EXAMPLE: TYPE59(RET)
     */
    private Integer graceMinutes;

    /*

    22. CHARGE FOR GRACE
    This field determines how charges are handled when the customer returns the vehicle after the grace period has expired.
    Y= Yes indicates the customer will be charged for the entire time he is late, grace period included.
    N= No indicates the customer will only be charged for the time beyond the grace period.
     */

    private Boolean chargeForGrace;

    /*
    23. DISCOUNTABLE
    Y= Discounts may be applied from the Reservation, RA Open or RA Close screens.
    N= Discounts are not allowed. Access to the discount fields on the Reservation, RA Open and RA Close screens will be blocked. Standard discounts recorded in the Customer File for repeat customers will be ignored.
     */
    private Boolean discountable;

    /*

    24. EDITABLE
    Y= Using the reservation screen or the Open and Close screens, rental rates can be edited by any user who has been granted the authority to edit rates in their Edit User Access screen.
    N= Rate fields in the Reservation, Open and Close screens may not be edited by any user.
    EXAMPLE: TypeY(RET)
     */

    private Boolean editable;



    private Integer minDaysForWeek;
    private Integer periodMaxDays;

    /*

    29a. DAYS PER MONTH
    For Rate Products with a monthly rate, enter the number of days that constitute a rental month.
    The exact meaning of this field and how it affects rental calculations is determined by the value entered into the second field on this line.
    This second field on line 29 is only accessible if a number is entered in the first field.
    After entering a number, the literal "Type" will appear and the cursor will advance to this field rather than line 30.
    EXAMPLE :28(RET)

     */

    private Integer daysPerMonth;


    private String commYn;
    private String commCat;

    /*
        default Type Codes can be entered on rates so that when that rate is selected for a RA, the Type Code field is populated automatically.
     */

    @ManyToOne
    @JoinColumn(name = "deflt_ra_type_id")
    private ValidTypeCode defltRaType;

    /*
        CORP STD
        C (Corporate Rate):

        This rate is designated as a corporate rate created by a corporate headquarters operation.
        It cannot be edited or a new version created through the Edit Local Rates program.
        It can only be viewed in the Edit Local Rates program.
        The rate can be loaded automatically by applying a valid CDPID number to the transaction.
        Users are blocked from adding the rate manually to a reservation or RA.

        L (Local Corporate Rate):

        This rate is designated as a local corporate rate.
        Similar restrictions apply as for the corporate rate (C), depending on system configurations.

        N (or blank) (Not a Corporate Negotiated Rate):

        This rate is not designated as a corporate negotiated rate.
        It does not have the same restrictions as corporate rates and can be handled more freely within the system.
     */

    private String corpStd;

    /*
    A search of the Rate File is available from the Reservation and RA Open screens using the F6 key. This field determines whether or not this Rate Product will be included or skipped when searching the Rate File.

    N = No, the rate will not be searchable

    Y = Yes, the rate will be searchable

    A = The rate is searchable, but the question "USE QUOTED RATES?" will not be prompted on the RA Open screen when the vehicle selected for the RA is of a different class than the rate class.

    EXAMPLE: Type Y (RET).
     */

    private Boolean searchable;

    /*
    34. HIDE?

    The rental agreement print program can be enhanced so that on rentals involving this rate, the following choices are available as to whether calculated charges will print on the RA:

    Y = Yes. Each time a RA is printed with this rate product, prompt the user with a question asking whether or not the rates should print on the RA.

    H = Hide. When a RA is printed with this rate product, the rates are always hidden and there is no prompt for the user.
    N (or blank) = No, rates and charges should print in detail on RAs with this rate product.


    User tip: When the rates are flagged as hidden, a phrase similar to the following can be configured to print:

    Your rental is covered by a special rate agreement and will be billed directly to the responsible party.

    EXAMPLE: Type N (RET)

    User tip: The 'hide rates' feature is also available through customer type, corporate account, and privilege code.

    NOTE: Not all RA print programs support this feature. Contact Thermeon's Customer Support Dept. regarding having this feature added to the RA print program.
     */
    private Boolean hide;

    private Boolean noInvoice;


    /*

    36. INCL OPT SET

    This field is used when a rate is inclusive. That is, the rate entered on page 3 is meant to include items such as coverages, taxes, airport fees, or other options.
    When items are included in a rate, an Option Set is used to define which items are included.
    (Option Sets are created in the Edit Option Set program.)

    When a rate with an Inclusive Option Set is used on the RA Open screen, the included items are blocked from being sold.
    When the RA posts, revenue for the included items is extracted from the gross rental rate and the various revenue accounts are credited.
    The rate is allocated amongst everything that is included.

    If this rate is an inclusive rate, enter in this field the appropriate Option Set that defines the included items.

    To search the Option Set file, press Shift-F7 from this field.

    EXAMPLE: Type A (RET)

    User tip: If "change to" rates (page 2) or alternate rates (below) are referenced on a Rate Product that has an Included Option Set, the same Option Set must be present on the referenced rates.

    For more information on Inclusive Rates, refer to the chapter in this manual titled Overview - Inclusive Rates.

     */

    @ManyToOne
    @JoinColumn(name = "incl_opt_set_id")
    private OptionSet inclOptSet;

    @ManyToOne
    @JoinColumn(name = "addon_opt_set_id")
    private OptionSet addonOptSet;


    /*
     * Class Codes associated with this Rate Product.
     *
     * This field defines the vehicle classes that this Rate Product applies to.
     * It establishes a many-to-many relationship between RateProduct and ClassCode entities.
     *
     * When a RateProduct is created or updated, the associated ClassCodes should be defined.
     * If you answer "Y" to the prompt "LOAD ALL CLASSES INTO PAGE 4?", all Vehicle Class Codes
     * defined for your terminal's default location will be loaded into this list.
     *
     * This association ensures that the RateProduct is applicable to the appropriate vehicle
     * classes as per the business requirements.
    */

    @ManyToMany
    @JoinTable(
            name = "rate_product_class_codes",
            joinColumns = @JoinColumn(name = "rate_product_id"),
            inverseJoinColumns = @JoinColumn(name = "class_code_id")
    )
    private List<ClassCode> classCodes = new ArrayList<>();


    private Boolean fpoIncMan;
    private Float oneWayMiles = 0.0f;

    private String altRate1;

    /*
    Currency Field Explanation
    Purpose:
    Indicates Currency: Specifies if the rates on the third page are in a foreign currency.
    Usage:
    Foreign Currency: Enter the 3-character currency code as set up in the Edit Exchange Rates program.
    Local Currency: Leave the field blank if rates are in local currency.
    Requirements:
    Multi-Currency Mode: This field is mandatory if the system operates in Multi-Currency Mode, even for local currency rates.
    Example:
    If rates are in USD and your local currency is EUR, you would enter "USD" in this field.
    Note:
    Options Matching: Only options with the same currency code as the rate will be offered in reservations with this rate product.
     */
    private String currency;

    private Boolean calendarDayCalc;

    /*
    Status Flag Overview
    The Status Flag attribute in CARS+ determines the usability of a Rate Product. It can have three values:

    Blank (default): The Rate Product is effective as per its specified effective date and time.
    T (Terminated): The Rate Product is unusable for new reservations or rental agreements (RAs), but remains valid for existing ones. It cannot be purged from the system. It can be reactivated by removing the termination flag.
    P (Purged): The Rate Product is flagged for removal and behaves as if it does not exist for new transactions. It is eligible for purging based on additional criteria.
    Key Points:
    Terminated Rates: Cannot be used for new reservations or RAs, but remain in the system for reference and can be reactivated.
    Purged Rates: Are skipped during searches and can be removed from the system if they meet purge criteria.
    Example Use:

    Type T to terminate a rate product.
    This attribute helps manage the lifecycle of rate products efficiently, ensuring that outdated or irrelevant rates do not clutter the system while preserving the integrity of existing transactions.
     */

    private Boolean statusFlag;

    private Float hoursPct = 0.0f;
    private Float yieldPcts = 0.0f;
    private Float tierPcts = 0.0f;
    private Boolean allowLocal;
    private String segmentCode;
    private Float hrsChgToDay = 0.0f;
    private Float hrsXhrsLogic = 0.0f;
    private Boolean milesInIncl;
    private Boolean exemptOptSet;
    private Boolean discOnlyTime;
    private Boolean seamlessEdits;
    private Boolean seamEditRate;
    private Boolean excludeBillto;
    private Boolean ratePrepaid;

    /*
    PAID/FREE DAY Overview
    The PAID/FREE DAY field is used to define promotional rates that offer free rental days based on the number of paid rental days.

    Format: Paid days / Free days

    Example 1: 4/1
    Daily/Weekly/Other Rates: Pay for 4 days, get 1 day free.
    5-day rental: Pay for 4, get 1 free.
    10-day rental: Pay for 8, get 2 free.
    Weekly Example: 7/7
    Pay for 1 week, get 1 week free.
    Pay for 2 weeks, get 2 weeks free.
    Period Rates:

    For period rates, the free days are only given once, regardless of rental length.
    Example: 3/1 for a 1-3 day period.
    4-day rental: Get 1 free day.
    15-day rental: Still only 1 free day.
    User Tips:

    Free days will appear as a discount on the reservation or RA.
    Applicable to various rate types but not to certain insurance vouchers (N type IT vouchers, S subwindow insurance vouchers, I subwindow insurance vouchers).
    How to Enter:

    Type the paid days before the slash.
    Type the free days after the slash.
    Press (RET).
    This feature helps in creating promotional rate products that provide discounts by offering free rental days after a specified number of paid days.
     */
    private String paidFreeDay;

    /*


    EARLIEST PICKUP

    Enter the first day of the week a weekend rental can begin (MON, TUE, WED, THU, FRI, SAT, SUN).
    Example: THU
    TIME

    Enter the time of day that weekend rates become available.
    Example: 12N
    LATEST PICKUP

    Enter the last day of the week a weekend rental can begin (MON, TUE, WED, THU, FRI, SAT, SUN).
    Example: SAT
    TIME

    Enter the time of day that weekend rates are no longer available.
    Example: 6P
    EARLIEST RETURN

    Enter the first day a vehicle can be returned to qualify for the weekend rate (cannot be the same as the earliest pickup day, must be at least one day after).
    Example: SUN
    TIME

    Enter the earliest time a vehicle can be returned to qualify for the weekend rate.
    Example: 12N
    LATEST RETURN

    Enter the last day a vehicle can be returned to qualify for the weekend rate (MON, TUE, WED, THU, FRI, SAT, SUN).
    Example: MON
    TIME

    Enter the latest time a vehicle can be returned to qualify for the weekend rate.
    Example: 12N
     */

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

    private Float wkndRateMaxDays = 0.0f;

    private Boolean wkndOptions;
    private String startDayMtwfss;
    private String startDayChgTo;
    private Float minDays = 0.0f;
    private Float maxDays = 0.0f;
    private Float intercityChgTo = 0.0f;
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
    private Float tier1 = 0.0f;
    private Float tier2 = 0.0f;
    private Float tier3 = 0.0f;
    private Float tier4 = 0.0f;
    private Float miles = 0.0f;
    private Float intercity = 0.0f;

    private Float oneWayRate = 0.0f;
    private Float tier1KmRate = 0.0f;
    private Float tier1MRate = 0.0f;
    private Float tier2KmRate = 0.0f;
    private Float tier2MRate = 0.0f;
    private Float tier3KmRate = 0.0f;
    private Float tier3MRate = 0.0f;
    private Float tier4KmRate = 0.0f;
    private Float tier4MRate = 0.0f;


    // Page 3 fields
    private Boolean tollOptIn;
    private Boolean fixedTierRate;
    private Integer requoteDays;
    private String discCode;

    // Page 4 fields

    private Float dayRate = 0.0f;
    private Float weekRate = 0.0f;
    private Float monthRate = 0.0f;
    private Float xDayRate = 0.0f;

    private Float hourRate = 0.0f;
    private Float mileRate = 0.0f;
    private Float intercityRate = 0.0f;

    private String prc;
    private Boolean discount;


    // Audit fields
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;

    private Float modTime = 0.0f;

    private String modEmpl;
    private String empl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Float time;

    public Boolean isInclCvg1() {
        return inclCvg1;
    }

    public Boolean isInclCvg2() {
        return inclCvg2;
    }

    public Boolean isInclCvg3() {
        return inclCvg3;
    }

    public Boolean isInclCvg4() {
        return inclCvg4;
    }

    public Boolean isChargeForGrace() {
        return chargeForGrace;
    }

    public Boolean isDiscountable() {
        return discountable;
    }

    public Boolean isEditable() {
        return editable;
    }

    public Float getXDayRate() {
        return xDayRate != null ? xDayRate : 0.0f;
    }

}
