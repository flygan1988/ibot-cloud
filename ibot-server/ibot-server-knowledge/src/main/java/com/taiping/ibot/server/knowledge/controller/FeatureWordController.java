package com.taiping.ibot.server.knowledge.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.server.knowledge.entity.setting.FeatureWord;
import com.taiping.ibot.server.knowledge.service.IFeatureWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

/**
 * @author Fei
 */
@Slf4j
@RestController
@RequestMapping("feature_word")
public class FeatureWordController {
    @Autowired
    private IFeatureWordService featureWordService;

    @GetMapping("list")
    @PreAuthorize("hasAuthority('feature_word:view')")
    public IBotResponse selectFeatureWordList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        IPage<FeatureWord> page = featureWordService.getFeatureWords(pageNum, pageSize);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("search")
    public IBotResponse selectFeatureWordByCondition(@RequestParam(value = "field") String field,
                                                     @RequestParam(value = "value") String value,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        IPage<FeatureWord> page = featureWordService.getFeatureWordByCondition(field, value, pageNum, pageSize);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('feature_word:update')")
    public void updateFeatureWord(@RequestBody FeatureWord featureWord) {
        featureWordService.updateFeatureWord(featureWord);
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('feature_word:add')")
    public void addFeatureWord(@RequestBody FeatureWord featureWord) {
        featureWordService.addFeatureWord(featureWord);
    }

    @GetMapping("delete/{ids}")
    @PreAuthorize("hasAuthority('feature_word:delete')")
    public void deleteFeatureWord(@PathVariable("ids") String ids) {
        String[] idArray = ids.split(StringPool.COMMA);
        featureWordService.deleteFeatureWords(Arrays.asList(idArray));
    }
}
