package com.secret.platform.resEstimates;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        try {
            return LocalDateTime.parse(v, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            // Log the error or handle it accordingly
            throw new IllegalArgumentException("Invalid date-time format: " + v, e);
        }
    }


    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.format(DATE_FORMATTER);
    }
}
