package com.secret.platform.location;

import com.secret.platform.general_ledger.GeneralLedger;
import com.secret.platform.status_code.StatusCode;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.cache.spi.Region;

import java.util.Date;

@Entity
@Table(name = "locations", uniqueConstraints = {
        @UniqueConstraint(columnNames = "location_number"),
        @UniqueConstraint(columnNames = "holding_drawer")
})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("location_number")
    @Column(name = "location_number", nullable = false, unique = true)
    private String locationNumber;
    @JsonProperty("location_name")
    private String locationName;

    @JsonProperty("address_line1")
    private String addressLine1;

    @JsonProperty("address_line2")
    private String addressLine2;

    @JsonProperty("address_line3")
    private String addressLine3;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("profit_center_number")
    @Column(name = "profit_center_number", nullable = false, length = 3)
    private String profitCenterNumber;



    @JsonProperty("do_fuel_calc")
    @Column(name = "do_fuel_calc", nullable = false, length = 1)
    private String doFuelCalc;
    /* doFuelCalc:
            Enter 1 alpha character to indicate if the system should do automatic fuel charge calculations when RAs are closed or when vehicles are exchanged at this location.
    */

    @JsonProperty("holding_drawer")
    @Column(name = "holding_drawer", nullable = false, length = 3, unique = true)
    private String holdingDrawer;

    /* holding drawer:
        A holding drawer is a fictitious Cash Drawer that can be used for credit card payments when closing one-way rentals between owned locations (see the chapter on CARS+ Special Features for a detailed definition). Enter up to 3 alphanumeric characters to indicate the Holding Drawer number. (This is a required field.)
        EXAMPLE: Type 202(RET).
        Note: The holding drawer logic can be deactivated.
        User tip: Each cash drawer MUST have a unique number. That is, there cannot be a Drawer #1 at location A and another Drawer #1 at location B. Additionally, every renting location must have their own drawer number. (Locations cannot share a drawer.)
     */


    /*
        14. AUTO VEHICLE SELECT
        Enter 1 alpha character to indicate whether you wish to have your system automatically select the next vehicle when opening an RA.
        Y = After entering a Class Code on the RA Open screen the system will automatically select an available vehicle of that class with the lowest odometer reading. (Auto selection can be overridden by executing a vehicle search and selecting another vehicle.)
        N = The rental agent will have to enter a vehicle inventory number or select a vehicle from the Vehicle File Search sub window.
        EXAMPLE: Type N(RET).
        User tip: This feature is usually used in conjunction with the Dispatching features.
         */
    @JsonProperty("auto_vehicle_select")
    @Column(name = "auto_vehicle_select", nullable = false, length = 1)
    private String autoVehicleSelect;



    /*checkin status
        Enter 1 alpha character to select the vehicle Status Code assigned at Check-in. Codes used here must first exist in the Edit Status Code File.
        Those locations using the dispatch feature (see below) must enter a Status Code other than "A". (Status Code "A" under dispatching refers only to those vehicles currently on the ready line.)
        EXAMPLE: Type A(RET).
         */
    @ManyToOne
    @JoinColumn(name = "check_in_status_id", nullable = false)
    private StatusCode checkInStatus;

    /*

    17. CHECK-OUT FUEL
    Enter 1 numeric character to select the default fuel or electric vehicle's battery level for all vehicles checking out at this location in either RA Open or when opening a Non-Revenue Movement. The possible values are:
    7=There is no default for check out fuel or battery level. The field on the RAOPEN screen will default to the value "9" which is not a valid tank reading thus the rental agent will be required to manually enter the correct value. 
    8=Vehicles check-out with a full tank of fuel or battery level.
    9=Vehicles check-out with fuel or battery level entered at last check-in.
    EXAMPLE: Type8(RET).
    Note:If the settings should differ for electric vehicles, there is a setting that can be used on page 7, "Check-out EV level. That setting would take precedence for electric vehicles.

     */
    @JsonProperty("check_out_fuel")
    @Column(name = "check_out_fuel", nullable = false, length = 1)
    private String checkOutFuel;



    /*
        18. REGION
        Fuel rates can be managed by "region" instead of needing to be managed for each individual location. Regions are established by creating a non-renting location and assigning the member renting locations to that region. Therefore, a"region code" is a non-renting location representing a number of locations within a geographical region. This field is used to specify the region to which the location belongs.
        The following example uses California and the US to illustrate how regions can be established:
        Renting locations in Santa Ana, San Diego, and Los Angeles are members of the "Southern California" region. (A non-renting location for "Southern California" would need to be set up in the Location file.)
        The Southern, Central, and Northern California regions are members of the "California" region. (A non-renting location for "California" would need to be set up in the Location file.)
        The California region (along with regions representing other states in the western US) are members of the "Western" region. (A non-renting location for "Western" would need to be set up in the Location file.)
        The Western region (along with regions representing the central and eastern parts of the US) are members of the "National US" region. (A non-renting location for "National" would need to be set up in the Location file.)
        At present, this hierarchy is used ONLY by the "Min Gas Rate" and "Max Gas Chg" fields on page 4 of the location record and by the Edit Fuel Rate program itself. However, the region concept is planned to be used more in the future.
        If this location is a member of a "region", enter the region's location code in this field. (The region must already have a record in the Location file.)
        EXAMPLE: Type SC (RET)
         */
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Location region;

    /* valid rental loc
        38. VALID RENTAL LOC

        Enter:

        Y (or blank) = Yes, this location is a valid renting location.Reservations can be taken with it as the pick up location, and RAs can be opened at this location. (See important user tip below.)
        N = No, this location is not a renting location. Instead, this is a fictitious location which represents an accounting office, service center, sales lot, fuel region, etc. This location CANNOT be used as a pick up location on a reservation nor can RAs be opened at this location. Additionally, any vehicle whose current location is flagged as a non-valid renting location will not be counted as part of the total available fleet in the Planning Calendar. Please refer to the manual chapter Introduction to Fleet Planning for details.
        y = yes, This third value is valid only for Hertz and Dollar/Thrifty licensees. For Hertz this location is a renting location, but all RAs opened at this location will NOT be included in an electronic DRB batch file. (See important user tip below.)
        P = This location is a Park and Fly location.
        W = In WebRes, this location is a non-renting location, but it is a valid renting location in CARS+.
        C = In WebRes, this location can be used only as a closing location, but it is a valid renting location in CARS+ and can be used as an opening or closing location within CARS+.
        O = In WebRes, this location can be used only as an opening location, but it is a valid renting location in CARS+ and can be used as an opening or closing location.
        X = In Webres, this location can be used as an opening and closing location, but it is not a valid renting location in CARS+. (Cannot be used as opening or closing location in Reservations or RA Open.)
        n =This value means the same as "N" except that the location CAN bet used as a return location on a reservation or an RA. In addition the location is not subject to location cloning.
        F =This is a fleet pool location.
        R = Can be used as an opening or closing location for a Non-Rev movement, but cannot be used for Reservations or RAs. If full screen damage logic is used, and service vendors are set up as Locations, this type should be assigned to the Location.

        EXAMPLE: Type Y (RET).

        User tip: When a valid rental location is closed or temporarily closed, it is important to change any valid rental locations to a NON rental location. Please enter a "N" in this field.

     */
    @JsonProperty("valid_rental_loc")
    @Column(name = "valid_rental_loc", nullable = false, length = 1)
    private String validRentalLoc;

    /*
    19. DISPATCH CONTROL
    This flag determines whether this location will use the vehicle dispatch features.
    Y = Dispatch features will be used as follows:
    Open RAs will go on "Hold" status when saved and the Release Vehicle program must be used to change the status to "On-rent".
    The opening date/time is set when the RA is saved. It is not adjusted when the vehicle is released from Hold status.
    The Show Current Status report can be run for this location.
    On the Vehicle Search program, rental agents are restricted to searching only for vehicles with Status Code "A".
    When a RA is preprinted, the RA status is VOID-P.
    1 = The same Dispatch features as listed above in the “Y” response will be in effect with these exceptions:
    When the vehicle is released from Hold, the opening date/time of the RA will be re-written to the current system date/time when the release is entered.
    When a RA is preprinted, the RA status is HOLD-P.
    N = This location will not use the dispatch features.
    EXAMPLE: Type Y (RET).
     */

    @JsonProperty("dispatch_control")
    private String dispatchControl = "N";



    /*
            33. INTER-OFC A/R ACCT
            Enter a 4 digit asset account number from your Valid GL File to indicate which account should be affected by inter-office receivables. An inter-office receivable is created when a closing location receives payments for an RA that opened at another owned location. In order to accurately track inter-office receivables, each location should have their own unique account number.
            As an example, if an RA opened at location A and closes at location B with a cash payment, the payment would post as follows:
            When the DBR for location A is posted:
            a.Various revenue accounts are CREDITED.
            b.An account made up of the Inter-office account from location A and the profit center # from location B is DEBITED.
            This would indicate that location A is owed money from location B.
            When the DBR for location B is posted:
            a.The cash account for the cash drawer at location B is DEBITED.
            b.The same account that was debited by the posting of location A is CREDITED.
            The net result, when both locations have been posted is:
            a. CREDIT to revenue for location A
            b. DEBIT to cash for location B.
            This field is required. 
            EXAMPLE: Type 1051(RET).
         */
    @ManyToOne
    @JoinColumn(name = "inter_ofc_ar_acct_id", nullable = false)
    private GeneralLedger interOfcArAcct;

    /*
    34. METROPLEX LOCATION
    This field is related to the Fleet Planning feature of CARS+. Fleet planning maintains a "calendar" for each class of vehicles at each location, keeping track of how many vehicles are at each location and, of those, how many are committed by reservations or open RAs. Users are warned when a class of vehicles is overbooked.
    However, in metropolitan areas, one location could be blacked-out for a certain vehicle class while a second location a few miles away may still have uncommitted vehicles of the same class. To overcome this, locations within the same geographic area can be grouped together into a common pool of vehicles. This grouping is called "metroplex". Locations should be assigned to a metroplex grouping only when vehicles can be conveniently shuttled back and forth.
    Set up:
    1. A Location Group Code must be established for the metroplex. The location is assigned to a metroplex by entering the appropriate Location Group Code in this field .For metroplex purposes, a Group Code may not have other groups as members. All members of the group must be individual locations. All locations in the metroplex should have the Location Group Code entered in this field on their Location record.
    2. Activating the Metroplex feature impacts the manner in which several other control files in CARS+ should be configured. The manual chapter Introduction to Fleet Planning describes these issues and should be thoroughly reviewed prior to activating the Metroplex feature. 
    Entering anything in this field other than a valid Location Group Code (or a Group Code to which this location does not belong) will result in the following message display:
    INVALID METROPLEX GROUP - MUST BE A LOCATION GROUP CODE
    If this location belongs to a metroplex, enter the corresponding Location Group Code in this field. Remember, the first character of a Location Group Code is the plus sign (+).
    EXAMPLE: Type +SNA (RET)
     */

    @JsonProperty("metroplex_location")
    private String metroplexLocation;

    @JsonProperty("rate_set")
    private String rateSet;

    @JsonProperty("walkup_rate")
    private String walkupRate;

    @JsonProperty("net_rate_set")
    private String netRateSet;

    @JsonProperty("auth_swipe")
    private String authSwipe;

    @JsonProperty("owned_loc")
    private String ownedLoc;

    @JsonProperty("upsell")
    private String upsell;

    @JsonProperty("posting_comp")
    private String postingComp;

    @JsonProperty("posting_org")
    private String postingOrg;

    @JsonProperty("ra_cnt")
    private String raCnt;

    @JsonProperty("settle_flag")
    private String settleFlag;

    @JsonProperty("warn_maint_close")
    private String warnMaintClose;

    @JsonProperty("ff_comm_pct")
    private String ffCommPct;

    @JsonProperty("min_driver_age")
    private Integer minDriverAge;

    @JsonProperty("max_rntl_days")
    private Integer maxRntlDays;

    @JsonProperty("warn_overbooking")
    private String warnOverbooking;

    @JsonProperty("require_employer")
    private String requireEmployer;

    @JsonProperty("manual_post_dbr")
    private String manualPostDbr;

    @JsonProperty("enable_res_print")
    private String enableResPrint;

    @JsonProperty("max_age_rental")
    private Integer maxAgeRental;

    @JsonProperty("airport_location")
    private String airportLocation;

    @JsonProperty("res_warn_lvl2_hard")
    private String resWarnLvl2Hard;

    @JsonProperty("ra_warn_lvl2_hard")
    private String raWarnLvl2Hard;

    @JsonProperty("local_currency")
    private String localCurrency;

    @JsonProperty("print_vin_number")
    private String printVinNumber;

    @JsonProperty("drop_zone")
    private String dropZone;

    @JsonProperty("pkgd_cvg_1st")
    private String pkgdCvg1st;

    @JsonProperty("no_ldw_resp")
    private String noLdwResp;

    @JsonProperty("tax_renter")
    private String taxRenter;

    @JsonProperty("damage_option_code")
    private String damageOptionCode;

    @JsonProperty("contact_phone")
    private String contactPhone;

    @JsonProperty("federal_tax_id")
    private String federalTaxId;

    @JsonProperty("res_email")
    private String resEmail;

    @JsonProperty("drop_chg_rate_mile")
    private Float dropChgRateMile;

    @JsonProperty("transaction_types_allowed")
    private String transactionTypesAllowed;

    @JsonProperty("min_rental_length")
    private Integer minRentalLength;

    @JsonProperty("min_due_days")
    private Integer minDueDays;

    @JsonProperty("srchg_option")
    private String srchgOption;

    @JsonProperty("dropbox")
    private String dropbox;

    @JsonProperty("currency_symbol")
    private String currencySymbol;

    @JsonProperty("symbol_place")
    private String symbolPlace;

    @JsonProperty("tax_document_print_lib")
    private String taxDocumentPrintLib;

    @JsonProperty("refresh_rate")
    private Integer refreshRate;

    @JsonProperty("no_ra_copies")
    private String noRaCopies;

    @JsonProperty("print_estimate")
    private String printEstimate;

    @JsonProperty("td_print_through_date")
    private Date tdPrintThroughDate;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("min_ra_supply")
    private Integer minRaSupply;

    @JsonProperty("ins_expr_check")
    private String insExprCheck;

    @JsonProperty("abn")
    private String abn;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("report_email")
    private String reportEmail;

    @JsonProperty("ins_exempt_loc")
    private String insExemptLoc;

    @JsonProperty("do_toll")
    private String doToll;

    @JsonProperty("hotel_calc_ckin")
    private String hotelCalcCkin;

    @JsonProperty("hotel_ebds_time")
    private String hotelEbdsTime;

    @JsonProperty("unmask_empl_prompt")
    private String unmaskEmplPrompt;

    @JsonProperty("check_in_sheet")
    private String checkInSheet;

    @JsonProperty("block_one_way_from")
    private String blockOneWayFrom;

    @JsonProperty("block_one_way_to")
    private String blockOneWayTo;

    @JsonProperty("restrict_res_cancels")
    private String restrictResCancels;

    @JsonProperty("restrict_res_credits")
    private String restrictResCredits;

    @JsonProperty("zip_postal_code")
    private String zipPostalCode;

    @JsonProperty("longitude")
    private Float longitude;

    @JsonProperty("latitude")
    private Float latitude;

    @JsonProperty("feature_edits_profile")
    private String featureEditsProfile;

    @JsonProperty("require_addl_id")
    private String requireAddlId;

    @JsonProperty("time_zone")
    private String timeZone;

    @JsonProperty("iso_code")
    private String isoCode;

    @JsonProperty("brand_code")
    private String brandCode;

    @JsonProperty("req_gate_agent")
    private String reqGateAgent;

    @JsonProperty("force_close")
    private String forceClose;

    @JsonProperty("fpo_mandatory")
    private String fpoMandatory;

    @JsonProperty("gas_credit_opt")
    private String gasCreditOpt;

    @JsonProperty("discount_code")
    private String discountCode;

    @JsonProperty("rlsgld_ra_copies")
    private String rlsgldRaCopies;

    @JsonProperty("sub_acct")
    private String subAcct;

    @JsonProperty("cost_center")
    private String costCenter;

    @JsonProperty("resp_pct")
    private String respPct;

    @JsonProperty("do_amt_interface")
    private String doAmtInterface;

    @JsonProperty("tax_doc_deposits")
    private String taxDocDeposits;

    @JsonProperty("open_opt_tracking")
    private String openOptTracking;

    @JsonProperty("ask_charge_cc_fee")
    private String askChargeCcFee;

    @JsonProperty("do_carfirmation")
    private String doCarfirmation;

    @JsonProperty("guard_gate")
    private String guardGate;

    @JsonProperty("oag_code")
    private String oagCode;

    @JsonProperty("ask_business_rent")
    private String askBusinessRent;

    @JsonProperty("veh_search_group")
    private String vehSearchGroup;

    @JsonProperty("res_search_group")
    private String resSearchGroup;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", location number='" + locationNumber + '\'' +
                ", locationName='" + locationName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", phone='" + phone + '\'' +
                ", profitCenter='" + profitCenterNumber + '\'' +
                ", doFuelCalc='" + doFuelCalc + '\'' +
                ", holdingDrawer='" + holdingDrawer + '\'' +
                ", autoVehicleSelect='" + autoVehicleSelect + '\'' +
                ", checkInStatus='" + checkInStatus + '\'' +
                ", checkOutFuel='" + checkOutFuel + '\'' +
                ", region='" + region + '\'' +
                ", dispatchControl='" + dispatchControl + '\'' +
                ", interOfcArAcct='" + interOfcArAcct + '\'' +
                ", metroplexLocation='" + metroplexLocation + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfitCenterNumber() {
        return profitCenterNumber;
    }

    public void setProfitCenterNumber(String profitCenterNumber) {
        this.profitCenterNumber = profitCenterNumber;
    }

    public void setDoFuelCalc(String doFuelCalc) {
        this.doFuelCalc = doFuelCalc;
    }

    public String getHoldingDrawer() {
        return holdingDrawer;
    }

    public void setHoldingDrawer(String holdingDrawer) {
        this.holdingDrawer = holdingDrawer;
    }

    public String getDoFuelCalc() {
        return this.doFuelCalc;
    }

    public String getAutoVehicleSelect() {
        return autoVehicleSelect;
    }

    public void setAutoVehicleSelect(String autoVehicleSelect) {
        this.autoVehicleSelect = autoVehicleSelect;
    }

    public StatusCode getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(StatusCode checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    public String getCheckOutFuel() {
        return checkOutFuel;
    }

    public void setCheckOutFuel(String checkOutFuel) {
        this.checkOutFuel = checkOutFuel;
    }

    public Location getRegion() {
        return region;
    }

    public void setRegion(Location region) {
        this.region = region;
    }

    public String getDispatchControl() {
        return dispatchControl;
    }

    public void setDispatchControl(String dispatchControl) {
        this.dispatchControl = dispatchControl;
    }



    public String getMetroplexLocation() {
        return metroplexLocation;
    }

    public void setMetroplexLocation(String metroplexLocation) {
        this.metroplexLocation = metroplexLocation;
    }

    public String getValidRentalLoc() {
        return validRentalLoc;
    }

    public void setValidRentalLoc(String validRentalLoc) {
        this.validRentalLoc = validRentalLoc;
    }

    public GeneralLedger getInterOfcArAcct() {
        return interOfcArAcct;
    }

    public void setInterOfcArAcct(GeneralLedger interOfcArAcct) {
        this.interOfcArAcct = interOfcArAcct;
    }
}
