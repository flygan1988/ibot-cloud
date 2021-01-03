package com.taiping.ibot.server.knowledge.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.server.knowledge.entity.kg.*;
import com.taiping.ibot.server.knowledge.service.IKgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

/**
 * @author Fei
 */
@Slf4j
@RestController
@RequestMapping("kg")
public class KgController  {
    @Autowired
    private IKgService kgService;

    @PostMapping("saveProduct")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse addProduct(@RequestBody Product product) {
        Object obj = kgService.saveEntity(product);
        if (obj == null) return new IBotResponse().message("新增/修改产品实体失败");
        return new IBotResponse().data(obj);
    }

    @PostMapping("saveDisease")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse addDisease(@RequestBody Disease disease) {
        Object obj = kgService.saveEntity(disease);
        return new IBotResponse().data(obj);
    }

    @PostMapping("saveClause")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse addClause(@RequestBody Clause clause) {
        Object obj = kgService.saveEntity(clause);
        return new IBotResponse().data(obj);
    }

    @PostMapping("saveCompany")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse addCompany(@RequestBody Company company) {
        Object obj = kgService.saveEntity(company);
        return new IBotResponse().data(obj);
    }

    @PostMapping("saveTerm")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse addTerm(@RequestBody Term term) {
        Object obj = kgService.saveEntity(term);
        return new IBotResponse().data(obj);
    }

    @PostMapping("saveIMoney")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse addIMoney(@RequestBody IMoney iMoney) {
        Object obj = kgService.saveEntity(iMoney);
        return new IBotResponse().data(obj);
    }

    @PostMapping("saveOpRule")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse addOpRule(@RequestBody OpRule opRule) {
        Object obj = kgService.saveEntity(opRule);
        return new IBotResponse().data(obj);
    }

    @GetMapping("deleteEntity")
    @PreAuthorize("hasAuthority('kg:delete')")
    public IBotResponse delEntity(@RequestParam(value = "type", required = true) String type,
                                  @RequestParam(value = "id", required = true) Long id) {
        if ("product".equalsIgnoreCase(type)) {
            kgService.deleteProductById(id);
        }else if ("company".equalsIgnoreCase(type)) {
            kgService.deleteCompanyById(id);
        }else if ("clause".equalsIgnoreCase(type)) {
            kgService.deleteClauseById(id);
        }else if ("term".equalsIgnoreCase(type)) {
            kgService.deleteTermById(id);
        }else if ("disease".equalsIgnoreCase(type)) {
            kgService.deleteDiseaseById(id);
        }else if ("imoney".equalsIgnoreCase(type)) {
            kgService.deleteIMoneyById(id);
        }else if ("oprule".equalsIgnoreCase(type)) {
            kgService.deleteOpRule(id);
        }else {
            return new IBotResponse().message("未知实体类型！");
        }
        return new IBotResponse().message("节点保存成功");
    }

    @GetMapping("getEntity")
    public IBotResponse getEntity(@RequestParam(value = "type") String type,
                                  @RequestParam(value = "id") Long id) {
        if ("product".equalsIgnoreCase(type)) {
            Product product = kgService.getProductEntityById(id);
            return new IBotResponse().data(product);
        }else if ("company".equalsIgnoreCase(type)) {
            Company company = kgService.getCompanyEntityById(id);
            return new IBotResponse().data(company);
        }else if ("clause".equalsIgnoreCase(type)) {
            Clause clause = kgService.getClauseEntityById(id);
            return new IBotResponse().data(clause);
        }else if ("term".equalsIgnoreCase(type)) {
            Term term = kgService.getTermEntityById(id);
            return new IBotResponse().data(term);
        }else if ("disease".equalsIgnoreCase(type)) {
            Disease disease = kgService.getDiseaseEntityById(id);
            return new IBotResponse().data(disease);
        }else if ("imoney".equalsIgnoreCase(type)) {
            IMoney iMoney = kgService.getIMoneyById(id);
            return new IBotResponse().data(iMoney);
        }else if ("oprule".equalsIgnoreCase(type)) {
            OpRule opRule = kgService.getOpRuleById(id);
            return new IBotResponse().data(opRule);
        }
        return new IBotResponse().message("未知实体类型！");
    }

    @GetMapping("getEntityList")
    public IBotResponse getEntityList(@RequestParam(value = "type", required = true) String type,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        if ("product".equalsIgnoreCase(type)) {
            Page<Product> page = kgService.getProductList(PageRequest.of(pageNum-1, pageSize));
            Map<String, Object> data = getDataTable(page);
            return new IBotResponse().data(data);
        }else if ("company".equalsIgnoreCase(type)) {
            Page<Company> page = kgService.getCompanyList(PageRequest.of(pageNum-1, pageSize));
            Map<String, Object> data = getDataTable(page);
            return new IBotResponse().data(data);
        }else if ("disease".equalsIgnoreCase(type)) {
            Page<Disease> page = kgService.getDiseaseList(PageRequest.of(pageNum-1, pageSize));
            Map<String, Object> data = getDataTable(page);
            return new IBotResponse().data(data);
        }else if ("clause".equalsIgnoreCase(type)) {
            Page<Clause> page = kgService.getClauseList(PageRequest.of(pageNum-1, pageSize));
            Map<String, Object> data = getDataTable(page);
            return new IBotResponse().data(data);
        }else if ("term".equalsIgnoreCase(type)) {
            Page<Term> page = kgService.getTermList(PageRequest.of(pageNum-1, pageSize));
            Map<String, Object> data = getDataTable(page);
            return new IBotResponse().data(data);
        }
        return new IBotResponse().message("未知实体类型！");
    }

    @PostMapping("saveCoverRelation")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveCoverRelation(@RequestBody Cover cover) {
        Cover relation = (Cover) kgService.saveRelation(cover);
        return new IBotResponse().data(relation);
    }

    @PostMapping("saveSaleRelation")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveSaleRelation(@RequestBody Sale sale) {
        Sale relation = (Sale) kgService.saveRelation(sale);
        return new IBotResponse().data(relation);
    }

    @PostMapping("saveHaveRelation")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveHaveRelation(@RequestBody Have have) {
        Have relation = (Have) kgService.saveRelation(have);
        return new IBotResponse().data(relation);
    }

    @PostMapping("saveContainRelation")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveClauseRelation(@RequestBody Contain contain) {
        Contain relation = (Contain) kgService.saveRelation(contain);
        return new IBotResponse().data(relation);
    }

    @PostMapping("saveSubtitleRelation")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveSubtitleRelation(@RequestBody SubTitle subTitle) {
        SubTitle relation = (SubTitle) kgService.saveRelation(subTitle);
        return new IBotResponse().data(relation);
    }

    @PostMapping("saveRuleRelation")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveRuleRelation(@RequestBody KgRule rule) {
        KgRule relation = (KgRule) kgService.saveRelation(rule);
        return new IBotResponse().data(relation);
    }

    @GetMapping("saveSaleById")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveSaleById(@RequestParam("startNodeId") Long startNodeId,
                                         @RequestParam("endNodeId") Long endNodeId) {
        Company company = kgService.getCompanyEntityById(startNodeId);
        Product product = kgService.getProductEntityById(endNodeId);
        Sale sale = new Sale();
        sale.setStartNode(company);
        sale.setEndNode(product);
        kgService.saveRelation(sale);
        return new IBotResponse().message("保存成功");
    }

    @GetMapping("saveCoverById")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveCoverById(@RequestParam("startNodeId") Long startNodeId,
                                         @RequestParam("endNodeId") Long endNodeId) {
        Product product = kgService.getProductEntityById(startNodeId);
        Disease disease = kgService.getDiseaseEntityById(endNodeId);
        Cover cover = new Cover();
        cover.setStartNode(product);
        cover.setEndNode(disease);
        kgService.saveRelation(cover);
        return new IBotResponse().message("保存成功");
    }

    @GetMapping("saveHaveById")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveHaveById(@RequestParam("startNodeId") Long startNodeId,
                                         @RequestParam("endNodeId") Long endNodeId) {
        Product product = kgService.getProductEntityById(startNodeId);
        Term term = kgService.getTermEntityById(endNodeId);
        Have have = new Have();
        have.setStartNode(product);
        have.setEndNode(term);
        kgService.saveRelation(have);
        return new IBotResponse().message("保存成功");
    }

    @GetMapping("saveContainById")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveContainById(@RequestParam("startNodeId") Long startNodeId,
                                         @RequestParam("endNodeId") Long endNodeId) {
        Product product = kgService.getProductEntityById(startNodeId);
        Clause clause = kgService.getClauseEntityById(endNodeId);
        Contain contain = new Contain();
        contain.setStartNode(product);
        contain.setEndNode(clause);
        kgService.saveRelation(contain);
        return new IBotResponse().message("保存成功");
    }

    @GetMapping("saveRuleById")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveRuleById(@RequestParam("startNodeId") Long startNodeId,
                                        @RequestParam("endNodeId") Long endNodeId) {
        Product product = kgService.getProductEntityById(startNodeId);
        OpRule opRule = kgService.getOpRuleById(endNodeId);
        KgRule rule = new KgRule();
        rule.setStartNode(product);
        rule.setEndNode(opRule);
        kgService.saveRelation(rule);
        return new IBotResponse().message("保存成功");
    }

    @GetMapping("saveSubtitleById")
    @PreAuthorize("hasAuthority('kg:add')")
    public IBotResponse saveSubtitleById(@RequestParam("startNodeId") Long startNodeId,
                                     @RequestParam("endNodeId") Long endNodeId) {
        Clause clause = kgService.getClauseEntityById(startNodeId);
        IMoney iMoney = kgService.getIMoneyById(endNodeId);
        SubTitle subTitle = new SubTitle();
        subTitle.setStartNode(clause);
        subTitle.setEndNode(iMoney);
        kgService.saveRelation(subTitle);
        return new IBotResponse().message("保存成功");
    }

    @GetMapping("getAllInsureType")
    @PreAuthorize("hasAuthority('kg:view')")
    public IBotResponse getAllInsureType() {
        List<String> list = kgService.getAllProdType();
        return new IBotResponse().data(list);
    }

    @GetMapping("getProductByInsureType")
    public IBotResponse getProductByInsureType(@RequestParam("prodType") String prodType,
                                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Product> page = kgService.getProductByProdType(prodType, PageRequest.of(pageNum-1, pageSize));
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("getProductWithNullType")
    public IBotResponse getProductByInsureType(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Product> page = kgService.getProductWithNullType(PageRequest.of(pageNum-1, pageSize));
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("getProductBySaleStatus")
    public IBotResponse getProductBySaleStatus(@RequestParam("saleStatus") String saleStatus,
                                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Product> page = kgService.getProductBySaleStatus(saleStatus, PageRequest.of(pageNum-1, pageSize));
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("getProductsByCompanyId")
    public IBotResponse getProductsByCompanyId(@RequestParam("companyId") Long companyId) {
        List<Product> products = kgService.getProductsByCompanyId(companyId);
        return new IBotResponse().data(products);
    }

    @GetMapping("getDiseasesByProductId")
    public IBotResponse getDiseasesByProductId(@RequestParam("productId") Long productId) {
        List<Disease> diseases = kgService.getDiseasesByProductId(productId);
        return new IBotResponse().data(diseases);
    }

    @GetMapping("getClausesByProductId")
    public IBotResponse getClausesByProductId(@RequestParam("productId") Long productId) {
        List<Clause> clauses = kgService.getClausesByProductId(productId);
        return new IBotResponse().data(clauses);
    }

    @GetMapping("getTermsByProductId")
    public IBotResponse getTermsByProductId(@RequestParam("productId") Long productId) {
        List<Term> terms = kgService.getTermsByProductId(productId);
        return new IBotResponse().data(terms);
    }

    @GetMapping("getOpRulesByProductId")
    public IBotResponse getOpRulesByProductId(@RequestParam("productId") Long productId) {
        List<OpRule> opRules = kgService.getOpRuleByProductId(productId);
        return new IBotResponse().data(opRules);
    }

    @GetMapping("getIMoneysByClauseId")
    public IBotResponse getIMoneysByProductId(@RequestParam("clauseId") Long clauseId) {
        List<IMoney> iMonies = kgService.getIMoneyByClauseId(clauseId);
        return new IBotResponse().data(iMonies);
    }

    @GetMapping("searchProduct")
    public IBotResponse searchProduct(@RequestParam("query") String query) {
        List<Product> list = kgService.searchProduct(query);
        return new IBotResponse().data(list);
    }

    @PostMapping("batchUpdate")
    @PreAuthorize("hasAuthority('kg:add')")
    public void batchUpdate(String ids, Product product) {
        String[] idArray = StringUtils.split(ids, StringPool.COMMA);
        List<Long> list = new ArrayList<>(idArray.length);
        for (String id : idArray) {
            list.add(Long.valueOf(id));
        }
        kgService.batchUpdate(list, product);
    }

    @GetMapping("batchDelete")
    @PreAuthorize("hasAuthority('kg:delete')")
    public void batchDelete(String ids) {
        String[] idArray = StringUtils.split(ids, StringPool.COMMA);
        kgService.batchDelete(Arrays.asList(idArray));
    }

}
