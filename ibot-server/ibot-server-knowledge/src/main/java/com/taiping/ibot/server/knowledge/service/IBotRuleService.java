package com.taiping.ibot.server.knowledge.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.server.knowledge.entity.bot.BotRule;

import java.util.List;

public interface IBotRuleService extends IService<BotRule> {

    void deleteBotRuleByBotId(List<String> botIdList);

    void deleteBotRuleByRuleId(List<String> rIdList);
}
