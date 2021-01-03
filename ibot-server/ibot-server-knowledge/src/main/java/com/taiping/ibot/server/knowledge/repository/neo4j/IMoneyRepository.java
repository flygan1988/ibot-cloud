package com.taiping.ibot.server.knowledge.repository.neo4j;


import com.taiping.ibot.server.knowledge.entity.kg.IMoney;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMoneyRepository extends Neo4jRepository<IMoney, Long> {
}
