package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "cover")
@Data
@ToString
public class Cover {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Product startNode;

    @EndNode
    private Disease endNode;
}
