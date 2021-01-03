package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.common.entity.IBotConstant;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.utils.SortUtil;
import com.taiping.ibot.server.knowledge.entity.bot.Bot;
import com.taiping.ibot.server.knowledge.entity.bot.BotKnowledge;
import com.taiping.ibot.server.knowledge.mapper.BotMapper;
import com.taiping.ibot.server.knowledge.service.IBotKnowledgeService;
import com.taiping.ibot.server.knowledge.service.IBotRuleService;
import com.taiping.ibot.server.knowledge.service.IBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BotServiceImpl extends ServiceImpl<BotMapper, Bot> implements IBotService {

    @Autowired
    private IBotKnowledgeService botKnowledgeService;

    @Autowired
    private IBotRuleService botRuleService;

    @Override
    public IPage<Bot> findBotDetail(Bot bot, QueryRequest request) {
        Page<Bot> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "id", IBotConstant.ORDER_ASC, false);
        return this.baseMapper.findBotDetailPage(page, bot);
    }

    @Override
    @Transactional
    public void createBot(Bot bot) {
        save(bot);
        if (bot.getKnowledgeId() == null) return;
        String[] botKnowledgeIds = bot.getKnowledgeId().split(StringPool.COMMA);
        setBotKnowledge(bot, botKnowledgeIds);
    }

    @Override
    @Transactional
    public void updateBot(Bot bot) {
        this.baseMapper.updateById(bot);
        botKnowledgeService.remove(new LambdaQueryWrapper<BotKnowledge>().eq(BotKnowledge::getBotId, bot.getId()));
        if (bot.getKnowledgeId() == null) return;
        String[] botKnowledgeIds = bot.getKnowledgeId().split(StringPool.COMMA);
        setBotKnowledge(bot, botKnowledgeIds);
    }

    @Override
    @Transactional
    public void deleteBot(String[] ids) {
        List<String> botIdList = Arrays.asList(ids);
        this.baseMapper.deleteBatchIds(botIdList);
        botKnowledgeService.deleteBotKnowledgeByBotId(botIdList);
        botRuleService.deleteBotRuleByBotId(botIdList);
    }

    private void setBotKnowledge(Bot bot, String[] botKnowledgeIds) {
        List<BotKnowledge> list = new ArrayList<>();
        Arrays.stream(botKnowledgeIds).forEach(bkId -> {
            BotKnowledge botKnowledge = new BotKnowledge();
            botKnowledge.setBotId(bot.getId());
            botKnowledge.setKid(Long.valueOf(bkId));
            list.add(botKnowledge);
        });
        botKnowledgeService.saveBatch(list);
    }
}
