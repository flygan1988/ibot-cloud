package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Disease")
@Data
@ToString
public class Disease {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String diseaseName;

    @Property(name = "raw_name")
    private String diseaseRawName;

    @Property(name = "disease_type")
    private String diseaseType;

    @Property(name = "content")
    private String diseaseContent;

    @Property(name = "group")
    private String group;
}
