package com.secret.platform.location;

import com.secret.platform.general_ledger.GeneralLedger;
import com.secret.platform.group_code.GroupCodes;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.status_code.StatusCode;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "locations", uniqueConstraints = {
        @UniqueConstraint(columnNames = "location_number"),
        @UniqueConstraint(columnNames = "holding_drawer")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("location_number")
    @Column(name = "location_number", unique = true)
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
    @Column(name = "profit_center_number", length = 3)
    private String profitCenterNumber;

    @JsonProperty("do_fuel_calc")
    @Column(name = "do_fuel_calc", length = 1)
    private String doFuelCalc;

    @JsonProperty("holding_drawer")
    @Column(name = "holding_drawer", length = 3, unique = true)
    private String holdingDrawer;

    @JsonProperty("auto_vehicle_select")
    @Column(name = "auto_vehicle_select", length = 1)
    private String autoVehicleSelect;

    @ManyToOne
    @JoinColumn(name = "check_in_status_id")
    private StatusCode checkInStatus;

    @JsonProperty("check_out_fuel")
    @Column(name = "check_out_fuel", length = 1)
    private String checkOutFuel;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Location region;

    @JsonProperty("valid_rental_loc")
    @Column(name = "valid_rental_loc", length = 1)
    private String validRentalLoc;

    @JsonProperty("dispatch_control")
    private String dispatchControl = "N";

    @ManyToOne
    @JoinColumn(name = "inter_ofc_ar_acct_id")
    private GeneralLedger interOfcArAcct;

    @ManyToOne
    @JoinColumn(name = "metroplex_location_id")
    private GroupCodes metroplexLocation;

    @JsonProperty("allow_multi_language_ra")
    @Column(name = "allow_multi_language_ra", length = 1)
    private String allowMultiLanguageRa;

    @JsonProperty("allow_wait_ras")
    @Column(name = "allow_wait_ras", length = 1)
    private String allowWaitRas;

    @ManyToOne
    @JoinColumn(name = "rate_set_id")
    private RateSet rateSet;

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
}
