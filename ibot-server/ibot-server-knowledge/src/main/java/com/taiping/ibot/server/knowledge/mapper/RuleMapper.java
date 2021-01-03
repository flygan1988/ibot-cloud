package com.taiping.ibot.server.knowledge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taiping.ibot.server.knowledge.entity.qa.Rule;
import org.apache.ibatis.annotations.Param;

/**
 * @author Fei
 */
public interface RuleMapper extends BaseMapper<Rule> {

    /**
     * 查找规则详细信息
     * @param page
     * @param rule
     * @return
     */
    IPage<Rule> findRuleDetailPage(Page page, @Param("rule") Rule rule);

}
