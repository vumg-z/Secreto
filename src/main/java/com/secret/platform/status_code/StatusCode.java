package com.secret.platform.status_code;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "status_codes")
public class StatusCode {
    public StatusCode() {
        // Default constructor
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("code")
    @Column(name = "code", nullable = false, unique = true, length = 1)
    private String code;

    @JsonProperty("description")
    @Column(name = "description", nullable = false, length = 20)
    private String description;

    @JsonProperty("status_type")
    @Column(name = "status_type", nullable = false, length = 1)
    private String statusType;

    @JsonProperty("note1")
    @Column(name = "note1")
    private String note1;

    @JsonProperty("note2")
    @Column(name = "note2")
    private String note2;

    /*
    hist_svc_code

    This field is used for Type "S" Status Codes. When changing the status of a vehicle to that of a Sold Type through the use of the Sell A Vehicle program, a record is also written to the Vehicle History File recording the event.
    Enter in this field the Service Code that should be used when writing the History Record. 
    Warning: If this field is left blank on a S type Status Code, the Sell A Vehicle program will NOT allow any vehicle to be marked as having been sold. 
    Note: If you "un-sell" a sold vehicle, a second Vehicle History record is written using this same Service Code to record that fact. 
    EXAMPLE: Type 95 (RET).

     */
    @JsonProperty("hist_svc_code")
    @Column(name = "hist_svc_code")
    private String histSvcCode;

    @JsonProperty("correctable_by_physical_inventory")
    @Column(name = "correctable_by_physical_inventory", nullable = false, length = 1)
    private String correctableByPhysicalInventory;

    @JsonProperty("depreciation_flag")
    @Column(name = "depreciation_flag", nullable = false, length = 1)
    private String depreciationFlag;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    public String getHistSvcCode() {
        return histSvcCode;
    }

    public void setHistSvcCode(String histSvcCode) {
        this.histSvcCode = histSvcCode;
    }

    public String getCorrectableByPhysicalInventory() {
        return correctableByPhysicalInventory;
    }

    public void setCorrectableByPhysicalInventory(String correctableByPhysicalInventory) {
        this.correctableByPhysicalInventory = correctableByPhysicalInventory;
    }

    public String getDepreciationFlag() {
        return depreciationFlag;
    }

    public void setDepreciationFlag(String depreciationFlag) {
        this.depreciationFlag = depreciationFlag;
    }
}
