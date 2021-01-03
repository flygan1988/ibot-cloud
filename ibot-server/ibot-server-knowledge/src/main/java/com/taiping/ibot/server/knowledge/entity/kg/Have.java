package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "have")
@Data
@ToString
public class Have {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Product startNode;

    @EndNode
    private Term endNode;
}
