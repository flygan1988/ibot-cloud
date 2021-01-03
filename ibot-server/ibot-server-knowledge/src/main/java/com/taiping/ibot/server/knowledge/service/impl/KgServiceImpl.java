package com.taiping.ibot.server.knowledge.service.impl;



import com.taiping.ibot.server.knowledge.entity.kg.*;
import com.taiping.ibot.server.knowledge.repository.neo4j.*;
import com.taiping.ibot.server.knowledge.service.IKgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KgServiceImpl implements IKgService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClauseRepository clauseRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ContainRepository containRepository;

    @Autowired
    private CoverRepository coverRepository;

    @Autowired
    private HaveRepository haveRepository;

    @Autowired
    private IMoneyRepository moneyRepository;

    @Autowired
    private OpRuleRepository opRuleRepository;

    @Autowired
    private KgRuleRepository kgRuleRepository;

    @Autowired
    private SubTitleRepository subTitleRepository;

    /**
     * 添加实体
     * @param obj
     * @return
     */
    @Override
    public Object saveEntity(Object obj) {
        if (obj instanceof Product) {
            return productRepository.save((Product) obj);
        }else if (obj instanceof Disease) {
            return diseaseRepository.save((Disease) obj);
        }else if (obj instanceof Company) {
            return companyRepository.save((Company) obj);
        }else if (obj instanceof Clause) {
            return clauseRepository.save((Clause) obj);
        }else if (obj instanceof Term) {
            return termRepository.save((Term) obj);
        }else if (obj instanceof IMoney) {
            return moneyRepository.save((IMoney) obj);
        }else if (obj instanceof OpRule) {
            return opRuleRepository.save((OpRule) obj);
        }
        return null;
    }

    /**
     * 添加关系
     * @param relation
     * @return
     */
    @Override
    public Object saveRelation(Object relation) {
        if (relation instanceof Sale) {
            return saleRepository.save((Sale) relation);
        }else if (relation instanceof Contain) {
            return containRepository.save((Contain) relation);
        }else if (relation instanceof Cover) {
            return coverRepository.save((Cover) relation);
        }else if (relation instanceof Have) {
            return haveRepository.save((Have) relation);
        }else if (relation instanceof KgRule) {
            return kgRuleRepository.save((KgRule) relation);
        }else if (relation instanceof SubTitle) {
            return subTitleRepository.save((SubTitle) relation);
        }
        return null;
    }

    /**
     * 删除公司实体
     * @param id
     */
    @Override
    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    /**
     * 删除产品实体
     * @param id
     */
    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
        productRepository.deleteInsuranceResp();
        productRepository.deleteSingleNodes();
    }

    /**
     * 删除疾病实体
     * @param id
     */
    @Override
    public void deleteDiseaseById(Long id) {
        diseaseRepository.deleteById(id);
    }

    /**
     * 删除条款实体
     * @param id
     */
    @Override
    public void deleteClauseById(Long id) {
        clauseRepository.deleteById(id);
    }

    /**
     * 删除术语实体
     * @param id
     */
    @Override
    public void deleteTermById(Long id) {
        termRepository.deleteById(id);
    }

    /**
     * 删除赔付金额实体
     * @param id
     */
    @Override
    public void deleteIMoneyById(Long id) {
        moneyRepository.deleteById(id);
    }

    /**
     * 删除运营规则实体
     * @param id
     */
    @Override
    public void deleteOpRule(Long id) {
        opRuleRepository.deleteById(id);
    }

    /**
     * 获取单个产品实体
     * @param id
     * @return
     */
    @Override
    public Product getProductEntityById(Long id) {
        return productRepository.findById(id).get();
    }

    /**
     * 获取单个疾病实体
     * @param id
     * @return
     */
    @Override
    public Disease getDiseaseEntityById(Long id) {
        return diseaseRepository.findById(id).get();
    }

    /**
     * 获取单个条款实体
     * @param id
     * @return
     */
    @Override
    public Clause getClauseEntityById(Long id) {
        return clauseRepository.findById(id).get();
    }

    /**
     * 获取单个公司实体
     * @param id
     * @return
     */
    @Override
    public Company getCompanyEntityById(Long id) {
        return companyRepository.findById(id).get();
    }

    /**
     * 获取单个术语实体
     * @param id
     * @return
     */
    @Override
    public Term getTermEntityById(Long id) {
        return termRepository.findById(id).get();
    }

    /**
     * 获取赔付金额实体
     * @param id
     * @return
     */
    @Override
    public IMoney getIMoneyById(Long id) {
        return moneyRepository.findById(id).get();
    }

    /**
     * 获取运营规则实体
     * @param id
     * @return
     */
    @Override
    public OpRule getOpRuleById(Long id) {
        return opRuleRepository.findById(id).get();
    }


    /**
     * 分页获取公司列表
     * @param pageable
     * @return
     */
    @Override
    public Page<Company> getCompanyList(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    /**
     * 分页获取产品列表
     * @param pageable
     * @return
     */
    @Override
    public Page<Product> getProductList(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * 按照产品类型获取产品列表
     * @param prodType
     * @return
     */
    @Override
    public Page<Product> getProductByProdType(String prodType, Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int total = productRepository.getCountByProdType(prodType);
        List<Product> productList = productRepository.getProductByProdType(prodType, pageNum*pageSize, pageSize);
        Page<Product> page = new PageImpl<>(productList, pageable, total);
        return page;
    }

    /**
     * 分页获取空类型产品
     * @param pageable
     * @return
     */
    @Override
    public Page<Product> getProductWithNullType(Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int total = productRepository.getCountWithNullType();
        List<Product> productList = productRepository.getProductWithNullType(pageNum*pageSize, pageSize);
        Page<Product> page = new PageImpl<>(productList, pageable, total);
        return page;
    }

    /**
     * 获取所有产品类型
     * @return
     */
    @Override
    public List<String> getAllProdType() {
        return productRepository.getAllProdType();
    }

    /**
     * 按照售卖状态获取产品列表
     * @param saleStatus
     * @return
     */
    @Override
    public Page<Product> getProductBySaleStatus(String saleStatus, Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int total = productRepository.getCountBySaleStatus(saleStatus);
        List<Product> productList = productRepository.getProductBySaleStatus(saleStatus, pageNum*pageSize, pageSize);
        Page<Product> page = new PageImpl<>(productList, pageable, total);
        return page;
    }

    /**
     * 分页获取条款列表
     * @param pageable
     * @return
     */
    @Override
    public Page<Clause> getClauseList(Pageable pageable) {
        return clauseRepository.findAll(pageable);
    }

    /**
     * 分页获取术语列表
     * @param pageable
     * @return
     */
    @Override
    public Page<Term> getTermList(Pageable pageable) {
        return termRepository.findAll(pageable);
    }

    /**
     * 分页获取疾病列表
     * @param pageable
     * @return
     */
    @Override
    public Page<Disease> getDiseaseList(Pageable pageable) {
        return diseaseRepository.findAll(pageable);
    }

    /**
     * 获取产品列表
     * @param id
     * @return
     */
    @Override
    public List<Product> getProductsByCompanyId(Long id) {
        return companyRepository.saleProducts(id);
    }

    /**
     * 获取疾病列表
     * @param id
     * @return
     */
    @Override
    public List<Disease> getDiseasesByProductId(Long id) {
        return productRepository.coverDisease(id);
    }

    /**
     * 获取条款列表
     * @param id
     * @return
     */
    @Override
    public List<Clause> getClausesByProductId(Long id) {
        return productRepository.containClause(id);
    }

    /**
     * 获取术语列表
     * @param id
     * @return
     */
    @Override
    public List<Term> getTermsByProductId(Long id) {
        return productRepository.haveTerm(id);
    }

    /**
     * 获取运营规则列表
     * @param id
     * @return
     */
    @Override
    public List<OpRule> getOpRuleByProductId(Long id) {
        return productRepository.ruleOpRule(id);
    }

    /**
     * 获取赔付金额列表
     * @param id
     * @return
     */
    @Override
    public List<IMoney> getIMoneyByClauseId(Long id) {
        return clauseRepository.subTitleImoney(id);
    }

    /**
     * 模糊搜索产品
     * @param query
     * @return
     */
    @Override
    public List<Product> searchProduct(String query) {
        query = ".*" + query + ".*";
        return productRepository.searchProduct(query);
    }

    @Override
    public void batchUpdate(List<Long> ids, Product product) {
        Iterator<Product> iterator = productRepository.findAllById(ids).iterator();
        List<Product> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            if (product.getSaleChannel() != null) p.setSaleChannel(product.getSaleChannel());
            if (product.getSaleStatus() != null) p.setSaleStatus(product.getSaleStatus());
            if (product.getListArea() != null) p.setListArea(product.getListArea());
            if (product.getIsMain() != null) p.setIsMain(product.getIsMain());
            if (product.getStartDate() != null) p.setStartDate(product.getStartDate());
            list.add(p);
        }
        productRepository.saveAll(list);
    }

    @Override
    public void batchDelete(List<String> idList) {
        for (String id : idList) {
            productRepository.deleteById(Long.valueOf(id));
        }
    }
}
