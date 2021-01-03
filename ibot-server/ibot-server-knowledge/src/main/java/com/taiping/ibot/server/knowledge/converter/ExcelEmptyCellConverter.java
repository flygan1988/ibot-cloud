package com.taiping.ibot.server.knowledge.converter;

import com.wuwenze.poi.convert.ReadConverter;
import com.wuwenze.poi.exception.ExcelKitReadConverterException;

public class ExcelEmptyCellConverter implements ReadConverter {
    @Override
    public Object convert(Object o) throws ExcelKitReadConverterException {
        String value = (String) o;
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("$EMPTY_CELL$")) {
            return null;
        }
        return value;
    }
}
