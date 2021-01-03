package com.taiping.ibot.server.knowledge.entity.setting.enums;


public enum EntityType {
    COMPANY_NAME("company_name"), DISEASE_GROUP("disease_group"),PROD_PROPERTY("prod_property"),OTHER("other"),
    PROD_NAME("prod_name"),PROD_TYPE("prod_type"),CLAUSE_NAME("clause_name"),DISEASE_NAME("disease_name"),
    SALE_STATUS("sale_status"),TERM_NAME("term_name"),BXZR_SUB("bxzr_sub");

    private String type;

    EntityType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
