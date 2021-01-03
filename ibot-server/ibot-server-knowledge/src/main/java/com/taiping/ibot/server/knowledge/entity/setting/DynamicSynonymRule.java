package com.taiping.ibot.server.knowledge.entity.setting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DynamicSynonymRule {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String rule;
    private Boolean status;

    public void clean() {
        rule = rule.replaceAll("，",",");
    }
}
