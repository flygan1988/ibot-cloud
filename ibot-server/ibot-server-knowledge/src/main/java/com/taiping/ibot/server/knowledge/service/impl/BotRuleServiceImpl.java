package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.server.knowledge.entity.bot.BotRule;
import com.taiping.ibot.server.knowledge.mapper.BotRuleMapper;
import com.taiping.ibot.server.knowledge.service.IBotRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BotRuleServiceImpl extends ServiceImpl<BotRuleMapper, BotRule> implements IBotRuleService {
    /**
     * 根据botId删除bot-rule映射
     * @param botIdList
     */
    @Override
    public void deleteBotRuleByBotId(List<String> botIdList) {
        this.baseMapper.delete(new QueryWrapper<BotRule>().lambda().in(BotRule::getBotId, botIdList));
    }

    /**
     * 根据ruleId删除bot-rule映射
     * @param rIdList
     */
    @Override
    public void deleteBotRuleByRuleId(List<String> rIdList) {
        this.baseMapper.delete(new QueryWrapper<BotRule>().lambda().in(BotRule::getRuleId, rIdList));
    }
}
