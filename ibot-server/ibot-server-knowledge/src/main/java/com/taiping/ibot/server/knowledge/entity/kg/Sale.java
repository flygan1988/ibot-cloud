package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "Sale")
@Data
@ToString
public class Sale {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Company startNode;

    @EndNode
    private Product endNode;
}
