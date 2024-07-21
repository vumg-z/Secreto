package com.secret.platform.general_ledger;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "general_ledger")
public class GeneralLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("main_acct")
    @Column(name = "main_acct", nullable = false, length = 4)
    private String mainAcct;

    @JsonProperty("sub_acct")
    @Column(name = "sub_acct", nullable = false, length = 3)
    private String subAcct;

    @JsonProperty("description")
    @Column(name = "description", nullable = false, length = 25)
    private String description;

    @JsonProperty("posting_ref")
    @Column(name = "posting_ref", length = 10)
    private String postingRef;

    @JsonProperty("dealer_commission_flag")
    @Column(name = "dealer_commission_flag", length = 1)
    private String dealerCommissionFlag;

    @JsonProperty("res_incentive_flag")
    @Column(name = "res_incentive_flag", length = 1)
    private String resIncentiveFlag;

    @JsonProperty("balance_sheet_item")
    @Column(name = "balance_sheet_item", length = 1)
    private String balanceSheetItem;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainAcct() {
        return mainAcct;
    }

    public void setMainAcct(String mainAcct) {
        this.mainAcct = mainAcct;
    }

    public String getSubAcct() {
        return subAcct;
    }

    public void setSubAcct(String subAcct) {
        this.subAcct = subAcct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostingRef() {
        return postingRef;
    }

    public void setPostingRef(String postingRef) {
        this.postingRef = postingRef;
    }

    public String getDealerCommissionFlag() {
        return dealerCommissionFlag;
    }

    public void setDealerCommissionFlag(String dealerCommissionFlag) {
        this.dealerCommissionFlag = dealerCommissionFlag;
    }

    public String getResIncentiveFlag() {
        return resIncentiveFlag;
    }

    public void setResIncentiveFlag(String resIncentiveFlag) {
        this.resIncentiveFlag = resIncentiveFlag;
    }

    public String getBalanceSheetItem() {
        return balanceSheetItem;
    }

    public void setBalanceSheetItem(String balanceSheetItem) {
        this.balanceSheetItem = balanceSheetItem;
    }
}
