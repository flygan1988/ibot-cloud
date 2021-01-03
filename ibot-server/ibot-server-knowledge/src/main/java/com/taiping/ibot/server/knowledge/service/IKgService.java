package com.taiping.ibot.server.knowledge.service;


import com.taiping.ibot.server.knowledge.entity.kg.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IKgService {
    /**
     * 创建实体
     * @param obj
     * @return
     */
    Object saveEntity(Object obj);

    /**
     * 创建关系
     * @param relation
     * @return
     */
    Object saveRelation(Object relation);

    /**
     * 删除公司实体
     * @param id
     */
    void deleteCompanyById(Long id);

    /**
     * 删除产品实体
     * @param id
     */
    void deleteProductById(Long id);

    /**
     * 删除疾病实体
     * @param id
     */
    void deleteDiseaseById(Long id);

    /**
     * 删除条款实体
     * @param id
     */
    void deleteClauseById(Long id);

    /**
     * 删除术语实体
     * @param id
     */
    void deleteTermById(Long id);

    /**
     * 删除赔付金额实体
     * @param id
     */
    void deleteIMoneyById(Long id);

    /**
     * 删除运营规则实体
     * @param id
     */
    void deleteOpRule(Long id);

    /**
     * 通过ID获取产品实体
     * @param id
     * @return
     */
    Product getProductEntityById(Long id);

    /**
     * 通过ID获取疾病实体
     * @param id
     * @return
     */
    Disease getDiseaseEntityById(Long id);

    /**
     * 通过ID获取条款实体
     * @param id
     * @return
     */
    Clause getClauseEntityById(Long id);

    /**
     * 通过ID获取公司实体
     * @param id
     * @return
     */
    Company getCompanyEntityById(Long id);

    /**
     * 通过ID获取术语实体
     * @param id
     * @return
     */
    Term getTermEntityById(Long id);

    /**
     * 通过ID获取赔付金额实体
     * @param id
     * @return
     */
    IMoney getIMoneyById(Long id);

    /**
     * 通过ID获取运营规则实体
     * @param id
     * @return
     */
    OpRule getOpRuleById(Long id);

    /**
     * 分页获取公司列表
     * @param pageable
     * @return
     */
    Page<Company> getCompanyList(Pageable pageable);

    /**
     * 分页获取产品列表
     * @param pageable
     * @return
     */
    Page<Product> getProductList(Pageable pageable);

    /**
     * 按照产品类型获取产品列表
     * @param prodType
     * @return
     */
    Page<Product> getProductByProdType(String prodType, Pageable pageable);

    /**
     * 分页获取无分类产品
     * @param pageable
     * @return
     */
    Page<Product> getProductWithNullType(Pageable pageable);

    /**
     * 获取所有产品类型
     * @return
     */
    List<String> getAllProdType();

    /**
     * 按照售卖状态获取产品列表
     * @param saleStatus
     * @return
     */
    Page<Product> getProductBySaleStatus(String saleStatus, Pageable pageable);

    /**
     * 分页获取条款列表
     * @param pageable
     * @return
     */
    Page<Clause> getClauseList(Pageable pageable);

    /**
     * 分页获取术语列表
     * @param pageable
     * @return
     */
    Page<Term> getTermList(Pageable pageable);

    /**
     * 分页获取疾病列表
     * @param pageable
     * @return
     */
    Page<Disease> getDiseaseList(Pageable pageable);

    /**
     * 获取公司售卖的产品
     * @param id
     * @return
     */
    List<Product> getProductsByCompanyId(Long id);

    /**
     * 获取产品保的疾病
     * @param id
     * @return
     */
    List<Disease> getDiseasesByProductId(Long id);

    /**
     * 获取产品包括的条款
     * @param id
     * @return
     */
    List<Clause> getClausesByProductId(Long id);

    /**
     * 获取产品术语
     * @param id
     * @return
     */
    List<Term> getTermsByProductId(Long id);

    /**
     * 获取运营规则
     * @param id
     * @return
     */
    List<OpRule> getOpRuleByProductId(Long id);

    /**
     * 获取赔付金额
     * @param id
     * @return
     */
    List<IMoney> getIMoneyByClauseId(Long id);

    /**
     * 按字段raw_name模糊搜索节点
     * @param query
     * @return
     */
    List<Product> searchProduct(String query);

    void batchUpdate(List<Long> ids, Product product);

    void batchDelete(List<String> idList);

}
