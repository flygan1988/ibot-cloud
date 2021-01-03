package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.server.knowledge.entity.bot.BotKnowledge;
import com.taiping.ibot.server.knowledge.mapper.BotKnowledgeMapper;
import com.taiping.ibot.server.knowledge.service.IBotKnowledgeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BotKnowledgeServiceImpl extends ServiceImpl<BotKnowledgeMapper, BotKnowledge> implements IBotKnowledgeService {

    /**
     * 根据botId删除bot-knowledge映射
     * @param botIdList
     */
    @Override
    public void deleteBotKnowledgeByBotId(List<String> botIdList) {
        this.baseMapper.delete(new QueryWrapper<BotKnowledge>().lambda().in(BotKnowledge::getBotId, botIdList));
    }

    /**
     * 根据knowledgeId删除bot-knowledge映射
     * @param kIdList
     */
    @Override
    public void deleteBotKnowledgeByKId(List<String> kIdList) {
        this.baseMapper.delete(new QueryWrapper<BotKnowledge>().lambda().in(BotKnowledge::getKid, kIdList));
    }
}
