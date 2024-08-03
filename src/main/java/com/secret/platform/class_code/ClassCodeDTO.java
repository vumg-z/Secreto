package com.secret.platform.class_code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassCodeDTO {
    private String classCode;
    private String rateProductNumber;
    private String rateSetCode;
    private double dayRate;
    private double weekRate;
    private double monthRate;
    private double xDayRate;
    private double hourRate;
    private double mileRate;
}
