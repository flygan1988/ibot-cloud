package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Clause")
@Data
@ToString
public class Clause {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String clauseName;

    @Property(name = "content")
    private String clauseContent;

    @Property(name = "raw_name")
    private String clauseRawName;
}
