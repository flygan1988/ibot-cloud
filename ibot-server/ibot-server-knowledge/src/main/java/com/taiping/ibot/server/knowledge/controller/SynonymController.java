package com.taiping.ibot.server.knowledge.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.server.knowledge.entity.setting.DynamicSynonymRule;
import com.taiping.ibot.server.knowledge.service.ISynonymService;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

/**
 * @author Fei
 */
@Slf4j
@Validated
@RestController
@RequestMapping("synonym")
public class SynonymController {
    @Autowired
    private ISynonymService synonymService;

    @GetMapping("list")
    @PreAuthorize("hasAuthority('synonym:view')")
    public IBotResponse selectSynonymRule(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize) {
        IPage<DynamicSynonymRule> page = synonymService.selectSynonymRule(pageNum, pageSize);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("search")
    public IBotResponse searchSynonymRule(@RequestParam(value = "pageNum") int pageNum,
                                          @RequestParam(value = "pageSize") int pageSize,
                                          String query) {
        IPage<DynamicSynonymRule> page = synonymService.searchSynonymRule(pageNum, pageSize, query);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('synonym:add')")
    public void createSynonymRule(@RequestBody DynamicSynonymRule dynamicSynonymRule) {
        dynamicSynonymRule.clean();
        synonymService.createSynonymRule(dynamicSynonymRule);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('synonym:update')")
    public void updateSynonymRule(@RequestBody DynamicSynonymRule dynamicSynonymRule) {
        dynamicSynonymRule.clean();
        synonymService.updateSynonymRule(dynamicSynonymRule);
    }

    @GetMapping("delete/{ids}")
    @PreAuthorize("hasAuthority('synonym:delete')")
    public void deleteSynonymRule(@PathVariable("ids") String ids) {
        String[] idArray = ids.split(StringPool.COMMA);
        synonymService.deleteSynonymRule(idArray);
    }

    @PostMapping("import")
    @ResponseBody
    public void excelImport(@RequestParam(value = "uploadFile", required = true) MultipartFile file) throws IOException {
        List<DynamicSynonymRule> list = new ArrayList<>();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        ExcelKit.$Import(DynamicSynonymRule.class).readXlsx(file.getInputStream(), new ExcelReadHandler<DynamicSynonymRule>() {

            @Override
            public void onSuccess(int i, int i1, DynamicSynonymRule dynamicSynonymRule) {
                list.add(dynamicSynonymRule);
            }

            @Override
            public void onError(int i, int i1, List<ExcelErrorField> list) {
                Map<String, Object> map = new HashMap<>();
                map.put("sheetIndex", i);
                map.put("rowIndex", i1);
                map.put("errorFields", list);
                errorList.add(map);
            }
        });
    }
}
