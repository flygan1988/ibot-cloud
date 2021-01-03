package com.taiping.ibot.server.knowledge.entity.setting.enums;

public enum FeatureWordType {
    BUY_PROD("buy_prod"), INCLUDE("include"), PROD_DISEASE_TYPE("prod_disease_type"), BELONG("belong"),
    DEFINITION("definition"), DISTINCT("distinct"), PAY("pay"), IS("is"), HAS("has");

    private String type;

    FeatureWordType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
