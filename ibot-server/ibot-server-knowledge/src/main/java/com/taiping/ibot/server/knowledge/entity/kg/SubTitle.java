package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "subtitle")
@Data
@ToString
public class SubTitle {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Clause startNode;

    @EndNode
    private IMoney endNode;
}
