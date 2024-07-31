package com.secret.platform.options;

import com.secret.platform.general_ledger.GeneralLedger;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "options")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opt_code", nullable = false, unique = false, length = 10)
    private String optionCode;

    @Column(name = "short_desc")
    private String shortDesc;

    @Column(name = "long_desc")
    private String longDesc;

    @Column(name = "bundle", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean bundle = false;

    /*
    typeFlag
    C (Coverage):
    Represents insurance products offered to customers, such as Personal Accident Insurance (PAI) or Supplemental Liability Insurance (SLI).
    Appears in the insurance/coverage section of the RA.

    O (Other):
    Covers miscellaneous charges like additional driver fees, GPS rental, or child seats.
    Shown in the "Other" charges section of the RA.

    A (CSADJ):
    Customer Service Adjustments.
    Used for adjustments related to customer service issues.

    J (Non-taxable Adjustment):
    Used for non-taxable options, typically for after-tax concessions or adjustments.
    Shown as non-taxable adjustments on the RA.

    D (Drop):
    Drop-off charges when the vehicle is returned to a different location.
    Displayed in the drop-off charges section of the RA.

    X (DISC):
    Main discount type; only one discount of this type can exist.
    Additional discounts should use type 'N'.

    N (Negative Option):
    Used for additional discounts or negative charges.
    Displayed in the discounts section of the RA.

    F (Fuel):
    Charges related to fuel, including prepaid fuel and fuel charged at close.
    Only one fuel record should be set up.
    Displayed in the fuel charges section of the RA.

    L (LDW):
    Loss Damage Waiver, includes a responsibility level.
    Shown with a responsibility level indicating the customer’s liability.

    K (MILE/KM):
    Charges based on distance traveled.
    Displayed in the mileage charges section of the RA.

    M (Miscellaneous):
    Miscellaneous charges shown in Miscellaneous Charge fields on the RA Open and RA Close screens.
    Not carried forward in multi-month RA logic.

    P (Promo):
    Promotional offers.
    Displayed in the promotional charges section of the RA.

    t (Tax):
    Main tax (e.g., sales tax, VAT). Only one 't' type per location.
    Displayed in the tax section of the RA.

    R (TIME):
    Basic rental time charges.
    Displayed in the time charges section of the RA.

     */
    @Column(name = "type_flag")
    private String typeFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gl_account_id")
    private GeneralLedger glAccount;

    /*
    ECHO CLS (Echo Message on Close)
    This field causes a message concerning the option to display on the Open or Close screen. Here are the possible messages and their purposes:
    Y (Yes):
    Purpose: Prompts at Close to check if a charge should be removed (e.g., return of rented equipment).
    Message: "CHARGE FOR 'option name'? (Y/N)"
    O (Open):
    Purpose: Displays a prompt on the Open screen to remind the user about the option.
    Message: "CHARGE FOR 'option name'? (Y/N)"
    Q (Question):
    Purpose: Prompts once on the Open screen to remind the user to sell the option.
    Message: "CHARGE FOR 'option name'? (Y/N)"
    P (Apply and Remove):
    Purpose: Automatically adds the option to all RAs at Open and removes it at Close.
    User Tip: Use for authorization pads varying by vehicle class or exempt corporate accounts.
    M (Multi-month):
    Purpose: Similar to "P" but applies to all RAs in a series for multi-month rentals.
    T (Time Check):
    Purpose: Prompts at Close to verify time charges for the option.
    Message: "VERIFY TIME CHARGES FOR OPTION 'option name'"
    R (Release Vehicle):
    Purpose: Prompts in the Release Vehicle program to ask if the option should be added to the RA.
    Message: "ADD xxx?" (where xxx is the Option Code)
    N (No):
    Purpose: No prompt message is required.
    Usage Tips:
    Automatically applied options can be removed at Close if the echo message prompts the user and they respond with "N".
    The field is not relevant for the 9 basic rental charge items.
    Summary
    Y, O, Q, T: Display prompts to confirm or remind about optional charges.
    P, M: Automatically apply and remove options from RAs.
    R: Prompts in the Release Vehicle program.
    N: No prompt needed.
     */
    @Column(name = "echo")
    private String echo;

    /*
    ALLOW QTY
    (Allow Quantity)
    Enter:
    Y= Yes, this item may be sold in quantity. Options such a moving boxes, maps, and infant seats are examples of this.
    M= Multiple. This allows for multiple charges of the same product to be charged on one rental agreement as separate charges.
    Example: For Electric Vehicles that may be charged a flat fee for not returning the vehicle with a full battery charge, can be charged the same flat fee for any vehicle exchanges that may occur during the course of the rental, when the vehicle returned was not returned with a full battery charge. 
    N(or blank)= No, this item cannot be sold in quantity. Examples are LDW, taxes and surcharges.

    EXAMPLE: Type(RET)
    User tip: Items that are Type "M" (Miscellaneous) should be marked with "N". Quantity is not allowed in the Misc. Items pop-up window. To apply a multiple of a miscellaneous charge, edit the rate.
    This field has no relevance for the 9 basic rental charge items.
     */

    @Column(name = "allow_qty")
    private String allowQty;

    @Column(name = "ins_only")
    private String insOnly;

    @Column(name = "pass_thru")
    private String passThru;

    /*
    RPT AS REV (Report as Revenue)
    This field determines which items on the Rental Agreement (RA) will be included in the calculation of "revenue" on selected reports in CARS+. The following options are available:
    Y (Yes):
    Purpose: This option should be considered as revenue.
    Reports: The option will be included in the calculation of revenue on various reports, including the Accrual Report, Res Agent Incentive Report, Direct Bill Report, Corporate Account Report, RA Daily Revenue Report, Employee Performance Report, Hertz Canada Revenue Accrual Report, Optional Sales Yield Report, Revenue Analysis Report, Auto Close Questionable RAs Report, and Hertz Rate Quoted Report.
    N (No):
    Purpose: This option should not be considered as revenue.
    Reports: The option will be excluded from the revenue calculation on the listed reports but will still be reported in the Miscellaneous Column on the Accrual Report.
    X (Exclude):
    Purpose: This option should not report as revenue and will be excluded entirely from the Accrual Report.
    Reports: The option will not be included in any revenue calculation or report, resulting in an incomplete accrual of rental revenue.
    Blank:
    Purpose: Behavior varies across different reports (not recommended).
    Reports: The exact behavior of "blank" is documented in the chapter for each program, but it is recommended to avoid leaving this field blank for consistency.
    Notes:
    Basic Rental Charges: Time and Miles (less Discount and CSADJ) are already considered revenue items and a "Y" response is not allowed. For Fuel, Drop, and Miscellaneous charges, this field is relevant.
    Accrual Report Behavior:
    Y and Blank: The option is reported in the Option column.
    N: The option is reported in the Miscellaneous Column.
    X: The option is not reported at all.
    Example:
    TypeY (RET): Indicating that the option should be considered as revenue.
    User Tip: To maintain consistency across all reports, it is recommended to avoid leaving this field blank.

    05 SVADJ SRV ADJUST

     */
    @Column(name = "rpt_as_rev")
    private String rptAsRev;

    /*
    WEBRES VISIBLE
    This field controls the visibility of an option on WebRes, the Option pop-up in Reservations, RA Open, RA Close, and its availability to the ResOptions API of webXML.

    Possible Entries:

    Y (Yes): Visible to users in WebRes, Reservations, RA Open, RA Close, and available to the ResOptions API of webXML.
    R: Not visible in WebRes or Reservations. Visible only in RA Open/Close pop-up window.
    O: Available in Reservations, WebRes, and webXML, but not in RA Open/Close.
    Tip: For 'res only' options in rate product's Add-On, mark the rate product as 'res only'.
    Blank: Not visible in WebRes. Visible in Reservations, RA Open/Close, but not available to ResOptions API.
    H (Hyperlink): Visible in WebRes as a clickable hyperlink for more information.
    Example:

    Type Y (RET): Makes the option visible through WebRes, Reservations, RA Open/Close, and available to the ResOptions API.
    Summary:

    Y: Visible in WebRes, Reservations, RA Open/Close, available to ResOptions API.
    R: Not visible in WebRes or Reservations, only in RA Open/Close pop-up.
    O: Available in Reservations, WebRes, webXML, not in RA Open/Close.
    Blank: Not visible in WebRes, visible in Reservations and RA Open/Close, not available to ResOptions API.
    H: Visible as a hyperlink in WebRes, clickable for more information.
     */

    @Column(name = "web_res_visible")
    private String webResVisible;

    /*
    DUE REPORT
    This field is used to designate if the option should print on the following reports:
    Vehicles Due In Report
    Reservation Manifest (if options are requested)
    Vehicle Prep Report (if options are requested)
    Enter:
    Y=Yes, the short description for this Option should print on the reports listed.
    N(or blank) = Do not print this option on the reports listed.
    EXAMPLE: Type Y (RET)
     */

    @Column(name = "due_report")
    private Double dueReport;

    @Column(name = "due_penalty")
    private Double duePenalty;

    /*
    EXPIRE DATE
    This field is used to indicate that this option is no longer active and should not appear as a choice on the following reports:
    Revenue Analysis Report
    Extra Revenue Report
    Extra Revenue Report 2
    Employee Performance Report
    If the date range for the report is later than the expiration date entered, the Option will not be listed as a choice.
    This date should not be confused with the "Expire Date" in the Edit Optional Rates program which is used to turn off the ability to add an item to a contract.
     */

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "ins_inv_pys_cls")
    private String insInvPysCls;

    @Column(name = "asset_by_unit")
    private String assetByUnit;

    @Column(name = "eff_blk_rmv_typ")
    private String effBlkRmvTyp;

    /*
    START DATE
    This field is only effective for options that are defined as automatically applied to transactions.
    It prevents automatically applied charges from being applied to a RA that is based on a reservation that was created prior to the start date entered here.
    This allows an operation to not apply a charge to a rental that was created after the reservation was booked (the charge was unknown at the time of the creation of the reservation, so the client was not warned).
    This date does NOT apply to walk-up rentals. Walk-up rentals that open before this date but after an effective date for a rate for this option will have the option automatically applied.
    EXAMPLE: Press (RET)
     */

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "linked_opt")
    private String linkedOpt;

    @Column(name = "rest_ebds_auth_opt")
    private String restEbdsAuthOpt;

    @Column(name = "blk_1wy_miles_seq")
    private Integer blk1wyMilesSeq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_time")
    private String modifiedTime;

    @Column(name = "modified_employee")
    private String modifiedEmployee;

    @Column(name = "day_of_week_pricing")
    private String dayOfWeekPricing;

    @Column(name = "use_gold_opt_set_qty_parts")
    private String useGoldOptSetQtyParts;

    @Column(name = "est_asg_opt_pri_agt_rls")
    private String estAsgOptPriAgtRls;

    /*
    OPT SET CODE This field is used if a discounted bundle or package of options is offered.
     For details on how to set up and use this feature, refer to the chapter Overview - Option Discounts, Bundles, and /Packages.
     */

    // options entity

    @Column(name = "opt_set_code")
    private String optSetCode;

    @ElementCollection
    @CollectionTable(name = "opt_set_code_appended", joinColumns = @JoinColumn(name = "option_id"))
    @Column(name = "opt_set_code")
    private List<String> optSetCodeAppended = new ArrayList<>();

    @Override
    public String toString() {
        return "Options{" +
                "id=" + id +
                ", optionCode='" + optionCode + '\'' +
                '}';
    }



}
