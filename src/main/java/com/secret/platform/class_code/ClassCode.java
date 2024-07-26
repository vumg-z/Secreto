package com.secret.platform.class_code;

import com.secret.platform.location.Location;
import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.rate_product.RateProduct;
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

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    private String classCode = "";
    private String description = "";

    @ManyToOne
    @JoinColumn(name = "pricing_code_id", nullable = false)
    private PricingCode pricingCode;

    @ManyToOne
    @JoinColumn(name = "rate_product_id", nullable = true)
    private RateProduct rateProduct;

    private double dayRate = 0.0;
    private double weekRate = 0.0;
    private double monthRate = 0.0;


    private double xDayRate = 0.0;


    private double hourRate = 0.0;
    private double mileRate = 0.0;
    private double itCtyRate = 0.0;
    private double prcRate = 0.0;
    private double disRate = 0.0;

    private Integer minimumAge = 0;
    private String underAgeSrchg = "";
    private Integer resWarn1 = 0;
    private Integer resWarn2 = 0;
    private Integer classHierarchy = 0;
    private String edrbFlag = "";
    private String dropCategory = "";
    private String useInPlanning = "";
    private String ldwRespEx = "";
    private String addlIdReq = "";
    private String altDesc = "";
    private String classAuthPad = "";
    private String surcharge = "";
    private String assetTypeCode = "";
    private String conversionCode = "";
    private String url = "";
    private String discTimeOnly = "";
    private String bookWRes = "";
    private Integer lotScreenAva = 0;
    private Integer maxPassengers = 0;
    private Integer maxGvwr = 0;
    private String pct2DaysOut = "";
    private String reservable = "";
    private String minDaysForMetroplex = "";
    private String vlfRate = "";
}
