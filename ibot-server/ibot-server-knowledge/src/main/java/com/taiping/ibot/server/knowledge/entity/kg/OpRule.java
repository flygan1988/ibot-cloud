package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "OpRule")
@Data
@ToString
public class OpRule {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "raw_name")
    private String opRuleRawName;

    @Property(name = "insurance_rules")
    private String insuranceRules;

    @Property(name = "preservation_rules")
    private String preservationRules;
}
