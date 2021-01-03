package com.taiping.ibot.server.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.common.entity.IBotConstant;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.utils.SortUtil;
import com.taiping.ibot.server.knowledge.entity.bot.BotRule;
import com.taiping.ibot.server.knowledge.entity.qa.Rule;
import com.taiping.ibot.server.knowledge.mapper.RuleMapper;
import com.taiping.ibot.server.knowledge.service.IBotRuleService;
import com.taiping.ibot.server.knowledge.service.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements IRuleService {
    @Autowired
    private IBotRuleService botRuleService;

    @Override
    public IPage<Rule> findRuleDetailPage(Rule rule, QueryRequest request) {
        Page<Rule> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "id", IBotConstant.ORDER_ASC, false);
        return this.baseMapper.findRuleDetailPage(page, rule);
    }

    @Override
    @Transactional
    public void createRule(Rule rule) {
        this.save(rule);
        if (rule.getBotId() == null) return;
        String[] botIds = rule.getBotId().split(StringPool.COMMA);
        setBotRule(rule, botIds);
    }

    @Override
    @Transactional
    public void deleteRules(String[] ruleIds) {
        removeByIds(Arrays.asList(ruleIds));
        botRuleService.deleteBotRuleByRuleId(Arrays.asList(ruleIds));
    }

    @Override
    @Transactional
    public void updateRule(Rule rule) {
        this.baseMapper.updateById(rule);
        botRuleService.remove(new QueryWrapper<BotRule>().lambda().eq(BotRule::getRuleId, rule.getId()));
        if (rule.getBotId() == null) return;
        String[] botIds = rule.getBotId().split(StringPool.COMMA);
        setBotRule(rule, botIds);
    }

    private void setBotRule(Rule rule, String[] botIds) {
        List<BotRule> list = new ArrayList<>();
        Arrays.stream(botIds).forEach(botId -> {
            BotRule botRule = new BotRule();
            botRule.setBotId(Long.valueOf(botId));
            botRule.setRuleId(rule.getId());
            list.add(botRule);
        });
        botRuleService.saveBatch(list);
    }
}
