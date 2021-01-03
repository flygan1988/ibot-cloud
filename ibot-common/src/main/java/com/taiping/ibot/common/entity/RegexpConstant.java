package com.taiping.ibot.common.entity;

import java.util.regex.Pattern;

public class RegexpConstant {
    // 简单手机号正则（这里只是简单校验是否为 11位，实际规则更复杂）
    public static final String MOBILE_REG = "[1]\\d{10}";

    /**
     * 中文正则
     */
    public static final Pattern CHINESE = Pattern.compile("[\u4e00-\u9fa5]");
}
