package com.taiping.ibot.server.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.exception.IBotException;
import com.taiping.ibot.server.knowledge.entity.qa.KnowledgeInfo;
import com.taiping.ibot.server.knowledge.service.IKnowledgeInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("knowledge_info")
public class KnowledgeInfoController {

    private final IKnowledgeInfoService knowledgeInfoService;

    @GetMapping("list")
    @PreAuthorize("hasAuthority('knowledge:view')")
    public IBotResponse getKnowledgeList(KnowledgeInfo knowledgeInfo, QueryRequest request) {
        IPage<KnowledgeInfo> page = knowledgeInfoService.findKnowledgeInfoDetail(knowledgeInfo, request);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("all")
    public IBotResponse getAllKnowledgeList() {
        List<KnowledgeInfo> list = knowledgeInfoService.list();
        return new IBotResponse().data(list);
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('knowledge:add')")
    public void addKnowledge(@Valid KnowledgeInfo knowledgeInfo) {
        knowledgeInfoService.save(knowledgeInfo);
    }

    @GetMapping("delete/{kids}")
    @PreAuthorize("hasAuthority('knowledge:delete')")
    public void delKnowledge(@NotBlank(message = "{required}") @PathVariable String kids) {
        String[] ids = kids.split(StringPool.COMMA);
        knowledgeInfoService.deleteKnowledgeInfo(ids);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('knowledge:update')")
    public void updateKnowledge(@Valid KnowledgeInfo knowledgeInfo) throws IBotException{
        if (knowledgeInfo.getId() == null)
            throw new IBotException("知识库ID为空");
        knowledgeInfoService.updateById(knowledgeInfo);
    }
}
