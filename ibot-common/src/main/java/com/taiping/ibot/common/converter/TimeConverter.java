package com.taiping.ibot.common.converter;

import com.taiping.ibot.common.utils.DateUtil;
import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;

@Slf4j
public class TimeConverter implements WriteConverter {
    @Override
    public String convert(Object value) {
        if (value == null) {
            return StringUtils.EMPTY;
        } else {
            try {
                return DateUtil.formatCstTime(value.toString(), DateUtil.FULL_TIME_SPLIT_PATTERN);
            } catch (ParseException e) {
                String message = "时间转换异常";
                log.error(message, e);
                throw new ExcelKitWriteConverterException(message);
            }
        }
    }
}