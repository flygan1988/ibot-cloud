package com.taiping.ibot.server.knowledge.repository.neo4j;


import com.taiping.ibot.server.knowledge.entity.kg.Disease;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends Neo4jRepository<Disease, Long> {
}
