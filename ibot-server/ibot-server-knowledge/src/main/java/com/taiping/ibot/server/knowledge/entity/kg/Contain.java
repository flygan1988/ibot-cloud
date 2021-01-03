package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(value = "contain")
@Data
public class Contain {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Product startNode;

    @EndNode
    private Clause endNode;

}
