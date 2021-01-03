package com.taiping.ibot.server.knowledge.converter;

import com.wuwenze.poi.convert.ReadConverter;
import com.wuwenze.poi.exception.ExcelKitReadConverterException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExcelDateFieldConverter implements ReadConverter {
    @Override
    public Object convert(Object o) throws ExcelKitReadConverterException {
        String value = (String) o;
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("$EMPTY_CELL$")) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(value, formatter);
    }
}
