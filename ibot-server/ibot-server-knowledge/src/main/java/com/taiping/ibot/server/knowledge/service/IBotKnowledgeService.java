package com.taiping.ibot.server.knowledge.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.server.knowledge.entity.bot.BotKnowledge;

import java.util.List;

public interface IBotKnowledgeService extends IService<BotKnowledge> {

    void deleteBotKnowledgeByBotId(List<String> botIdList);

    void deleteBotKnowledgeByKId(List<String> kIdList);

}
