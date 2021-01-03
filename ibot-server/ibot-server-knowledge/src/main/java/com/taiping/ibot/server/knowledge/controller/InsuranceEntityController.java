package com.taiping.ibot.server.knowledge.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.server.knowledge.entity.setting.InsuranceEntity;
import com.taiping.ibot.server.knowledge.service.IInsuranceEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

/**
 * @author Fei
 */
@Slf4j
@Validated
@RestController
@RequestMapping("insur_entity")
public class InsuranceEntityController {
    @Autowired
    private IInsuranceEntityService insuranceEntityService;

    @GetMapping("list")
    @PreAuthorize("hasAuthority('insur_entity:view')")
    public IBotResponse selectInsurEntityList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        IPage<InsuranceEntity> page = insuranceEntityService.getEntities(pageNum, pageSize);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("search")
    public IBotResponse selectInsurEntityByCondition(@RequestParam(value = "field") String field,
                                                     @RequestParam(value = "value") String value,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        IPage<InsuranceEntity> page = insuranceEntityService.getEntityByCondition(field, value, pageNum, pageSize);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('insur_entity:update')")
    public void updateInsurEntity(@RequestBody InsuranceEntity entity) {
        insuranceEntityService.updateEntity(entity);
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('insur_entity:add')")
    public void addInsurEntity(@RequestBody InsuranceEntity entity) {
        insuranceEntityService.addEntity(entity);
    }

    @GetMapping("delete/{ids}")
    @PreAuthorize("hasAuthority('insur_entity:delete')")
    public void deleteInsurEntity(@PathVariable("ids") String ids) {
        String[] idArray = ids.split(StringPool.COMMA);
        insuranceEntityService.deleteEntities(Arrays.asList(idArray));
    }
}
