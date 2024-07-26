package com.secret.platform.class_code;

import lombok.Data;

@Data
public class ClassCodeDTO {
    private String classCode;
    private String rateProductNumber;
    private double dayRate;
    private double weekRate;
    private double monthRate;
    private double xDayRate;
    private double hourRate;
    private double mileRate;
}
