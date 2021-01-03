package com.taiping.ibot.server.knowledge.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.exception.IBotException;
import com.taiping.ibot.server.knowledge.entity.bot.Bot;
import com.taiping.ibot.server.knowledge.service.IBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

/**
 * @author Fei
 */
@Slf4j
@RestController
@Validated
@RequestMapping("bot")
public class BotController {

    @Autowired
    private IBotService botService;

    @GetMapping("list")
    @PreAuthorize("hasAuthority('bot:view')")
    public IBotResponse getBotList(Bot bot, QueryRequest request) {
        IPage<Bot> page = botService.findBotDetail(bot, request);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("all")
    public IBotResponse getAllBotList() {
        List<Bot> list = botService.list();
        return new IBotResponse().data(list);
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('bot:add')")
    public void addBot(@Valid Bot bot) {
        botService.createBot(bot);
    }

    @GetMapping("delete/{botIds}")
    @PreAuthorize("hasAuthority('bot:delete')")
    public void delBot(@NotBlank(message = "{required}") @PathVariable String botIds) {
        String[] ids = botIds.split(StringPool.COMMA);
        botService.deleteBot(ids);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('bot:update')")
    public void updateBot(@Valid Bot bot) throws IBotException{
        if (bot.getId() == null)
            throw new IBotException("机器人ID为空");
        botService.updateBot(bot);
    }

}
