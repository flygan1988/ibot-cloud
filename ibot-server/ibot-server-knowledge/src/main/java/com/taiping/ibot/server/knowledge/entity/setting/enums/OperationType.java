package com.taiping.ibot.server.knowledge.entity.setting.enums;

public enum OperationType {
    ADD("add"), UPDATE("update"), DELETE("del");
    private String operationType;

    OperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
