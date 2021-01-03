package com.taiping.ibot.server.knowledge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taiping.ibot.server.knowledge.entity.bot.Bot;
import org.apache.ibatis.annotations.Param;

public interface BotMapper extends BaseMapper<Bot> {

    /**
     * 查询机器人详细信息
     * @param page 分页对象
     * @param bot 查询对象
     * @return
     */
    IPage<Bot> findBotDetailPage(Page page, @Param("bot") Bot bot);

}
