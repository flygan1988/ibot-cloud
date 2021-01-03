package com.taiping.ibot.server.knowledge.repository.neo4j;


import com.taiping.ibot.server.knowledge.entity.kg.Clause;
import com.taiping.ibot.server.knowledge.entity.kg.IMoney;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClauseRepository extends Neo4jRepository<Clause, Long> {
    @Query("MATCH(c:Clause) -[s:subtitle]-> (m:IMoney) WHERE id(c)={clauseId} return m")
    List<IMoney> subTitleImoney(@Param("clauseId") Long clauseId);
}
