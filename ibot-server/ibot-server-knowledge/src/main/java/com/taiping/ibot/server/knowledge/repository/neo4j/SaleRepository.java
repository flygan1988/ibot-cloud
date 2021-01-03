package com.taiping.ibot.server.knowledge.repository.neo4j;


import com.taiping.ibot.server.knowledge.entity.kg.Sale;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends Neo4jRepository<Sale, Long> {
}
