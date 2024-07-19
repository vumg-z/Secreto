package com.secret.platform.class_code;

import com.secret.platform.pricing_code.PricingCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class_codes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String classCode;
    private String description;

    @ManyToOne
    @JoinColumn(name = "pricing_code_id", nullable = false)
    private PricingCode pricingCode;

    private Integer minimumAge;
    private String underAgeSrchg;
    private Integer resWarn1;
    private Integer resWarn2;
    private Integer classHierarchy;
    private String edrbFlag;
    private String dropCategory;
    private String useInPlanning;
    private String ldwRespEx;
    private String addlIdReq;
    private String altDesc;
    private String classAuthPad;
    private String surcharge;
    private String assetTypeCode;
    private String conversionCode;
    private String url;
    private String discTimeOnly;
    private String bookWRes;
    private Integer lotScreenAva;
    private Integer maxPassengers;
    private Integer maxGvwr;
    private String pct2DaysOut;
    private String reservable;
    private String minDaysForMetroplex;
    private String vlfRate;
}
