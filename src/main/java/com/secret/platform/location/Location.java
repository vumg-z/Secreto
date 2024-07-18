package com.secret.platform.location;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("auto_vehicle_select")
    private String autoVehicleSelect;

    @JsonProperty("check_in_status")
    private String checkInStatus;

    @JsonProperty("check_out_fuel")
    private String checkOutFuel;

    @JsonProperty("region")
    private String region;

    @JsonProperty("dispatch_control")
    private String dispatchControl;

    @JsonProperty("inter_ofc_ar_acct")
    private String interOfcArAcct;

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
}
