package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "IMoney")
@Data
@ToString
public class IMoney {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "raw_name")
    private String iMoneyRawName;

    @Property(name = "content_summary")
    private String contentSummary;

    @Property(name = "content")
    private String content;
}
