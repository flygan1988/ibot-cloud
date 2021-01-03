package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Term")
@Data
@ToString
public class Term {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String termName;

    @Property(name = "content")
    private String termContent;

    @Property(name = "raw_name")
    private String termRawName;
}
