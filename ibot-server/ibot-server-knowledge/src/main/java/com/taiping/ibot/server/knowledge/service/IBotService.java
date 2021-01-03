package com.taiping.ibot.server.knowledge.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.server.knowledge.entity.bot.Bot;

public interface IBotService extends IService<Bot> {

    /**
     * 查找机器人详细信息
     * @param bot
     * @param request
     * @return
     */
    IPage<Bot> findBotDetail(Bot bot, QueryRequest request);

    /**
     * 创建机器人
     * @param bot
     */
    void createBot(Bot bot);

    /**
     * 更新机器人信息
     * @param bot
     */
    void updateBot(Bot bot);

    /**
     * 删除机器人
     * @param ids
     */
    void deleteBot(String[] ids);
}
