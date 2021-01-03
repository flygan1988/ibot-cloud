package com.taiping.ibot.server.knowledge.repository.neo4j;


import com.taiping.ibot.server.knowledge.entity.kg.Company;
import com.taiping.ibot.server.knowledge.entity.kg.Product;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends Neo4jRepository<Company, Long> {

    @Query("MATCH(c:Company) -[s:sale]-> (p:Product) WHERE id(c)={companyId} return p")
    List<Product> saleProducts(@Param("companyId") Long companyId);
}
