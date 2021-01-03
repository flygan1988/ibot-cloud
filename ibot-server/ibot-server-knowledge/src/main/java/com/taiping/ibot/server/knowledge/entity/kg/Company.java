package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Company")
@Data
@ToString
public class Company {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String companyName;

    @Property(name = "raw_name")
    private String companyRawName;
}
