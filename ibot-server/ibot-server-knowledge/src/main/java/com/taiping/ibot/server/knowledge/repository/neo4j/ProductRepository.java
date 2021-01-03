package com.taiping.ibot.server.knowledge.repository.neo4j;



import com.taiping.ibot.server.knowledge.entity.kg.*;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends Neo4jRepository<Product, Long> {

    @Query("MATCH (p:Product) WHERE p.prod_type = {prodType} RETURN p ORDER BY p.name SKIP {skipNum} LIMIT {pageSize}")
    List<Product> getProductByProdType(@Param("prodType") String prodType, @Param("skipNum") int skipNum, @Param("pageSize") int pageSize);

    @Query("MATCH (p:Product) WHERE p.prod_type is null RETURN p ORDER BY p.name SKIP {skipNum} LIMIT {pageSize}")
    List<Product> getProductWithNullType(@Param("skipNum") int skipNum, @Param("pageSize") int pageSize);

    @Query("MATCH (p:Product) WHERE p.prod_type is null RETURN count(p)")
    int getCountWithNullType();

    @Query("MATCH (p:Product) WHERE p.prod_type = {prodType} RETURN COUNT(p)")
    int getCountByProdType(@Param("prodType") String prodType);

    @Query("MATCH (p:Product) RETURN DISTINCT p.prod_type")
    List<String> getAllProdType();

    @Query("MATCH (p:Product) WHERE p.sale_status STARTS WITH {saleStatus} RETURN p ORDER BY p.name SKIP {skipNum} LIMIT {pageSize}")
    List<Product> getProductBySaleStatus(@Param("saleStatus") String saleStatus, @Param("skipNum") int skipNum, @Param("pageSize") int pageSize);

    @Query("MATCH (p:Product) WHERE p.sale_status STARTS WITH {saleStatus} RETURN COUNT(p)")
    int getCountBySaleStatus(@Param("saleStatus") String saleStatus);

    @Query("MATCH(p:Product) -[c:contain]-> (a:Clause) WHERE id(p)={productId} return a")
    List<Clause> containClause(@Param("productId") Long productId);

    @Query("MATCH(p:Product) -[h:have]-> (t:Term) WHERE id(p)={productId} return t")
    List<Term> haveTerm(@Param("productId") Long productId);

    @Query("MATCH(p:Product) -[c:cover]-> (d:Disease) WHERE id(p)={productId} return d")
    List<Disease> coverDisease(@Param("productId") Long productId);

    @Query("MATCH(p:Product) -[r:rule]-> (op:OpRule) WHERE id(p)={productId} return op")
    List<OpRule> ruleOpRule(@Param("productId") Long productId);

    @Query("MATCH(p:Product) WHERE p.raw_name =~ {query} return p")
    List<Product> searchProduct(@Param("query") String query);

    @Query("match(n) where id(n)={id} detach delete n")
    void deleteProduct(@Param("id") Long id);

    @Query("match (n)-[r]-(m) where n.raw_name='保险责任' and not (n)–[]-(:Product) detach delete n,r,m")
    void deleteInsuranceResp();

    @Query("match (n) where not (n) -- () delete n")
    void deleteSingleNodes();



}
