package com.taiping.ibot.server.knowledge.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.exception.IBotException;
import com.taiping.ibot.server.knowledge.entity.qa.Rule;
import com.taiping.ibot.server.knowledge.service.IRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

/**
 * @author Fei
 */
@Slf4j
@RestController
@Validated
@RequestMapping("rule")
public class RuleController {
    @Autowired
    private IRuleService ruleService;

    @GetMapping("list")
    @PreAuthorize("hasAnyAuthority('rule:view')")
    public IBotResponse getRules(Rule rule, QueryRequest request){
        Map<String, Object> dataTable = getDataTable(ruleService.findRuleDetailPage(rule, request));
        return new IBotResponse().data(dataTable);
    }


    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('rule:add')")
    public void addRule(@RequestBody @Valid Rule rule) {
        ruleService.createRule(rule);
    }

    @GetMapping("delete/{ruleIds}")
    @PreAuthorize("hasAnyAuthority('rule:delete')")
    public  void deleteRule(@NotBlank(message = "{required}") @PathVariable String ruleIds) {
        String[] rids = ruleIds.split(StringPool.COMMA);
        ruleService.deleteRules(rids);
    }

    @PostMapping("update")
    @PreAuthorize("hasAnyAuthority('rule:update')")
    public void updateRule(@RequestBody @Valid Rule rule) throws IBotException{
        if (rule.getId() == null) throw new IBotException("规则ID为空");
        ruleService.updateRule(rule);
    }

}
