package com.taiping.ibot.server.knowledge.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.server.knowledge.entity.qa.Rule;

/**
 * @author Fei
 */
public interface IRuleService extends IService<Rule> {

    /**
     * 分页获取规则详细信息
     * @param rule
     * @param queryRequest
     * @return
     */
    IPage<Rule> findRuleDetailPage(Rule rule, QueryRequest queryRequest);

    /**
     * 创建规则
     * @param rule
     */
    void createRule(Rule rule);

    /**
     * 删除规则
     * @param ruleIds
     */
    void deleteRules(String[] ruleIds);

    /**
     * 更新规则
     * @param rule
     */
    void updateRule(Rule rule);


}
