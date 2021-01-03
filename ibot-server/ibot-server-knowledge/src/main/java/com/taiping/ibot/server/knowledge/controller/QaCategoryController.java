package com.taiping.ibot.server.knowledge.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.common.exception.IBotException;
import com.taiping.ibot.server.knowledge.entity.qa.QaCategory;
import com.taiping.ibot.server.knowledge.service.IQaCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fei
 */
@Slf4j
@RestController
@Validated
@RequestMapping("category")
public class QaCategoryController {

    @Autowired
    private IQaCategoryService categoryService;

    @GetMapping("list")
    public IBotResponse getCategoryList(QaCategory category) {
        List<QaCategory> list = categoryService.findQaCategoryList(category);
        return new IBotResponse().data(list);
    }

    @PostMapping("add")
    public void addCategory(@Valid QaCategory category) {
        categoryService.save(category);
    }

    @GetMapping("delete/{cids}")
    public void delCategory(@NotBlank(message = "{required}") @PathVariable String cids) {
        String[] ids = cids.split(StringPool.COMMA);
        categoryService.deleteQaCategory(Arrays.asList(ids));
    }

    @PostMapping("update")
    public void updateCategory(@Valid QaCategory category) throws IBotException{
        if (category.getId() == null)
            throw new IBotException("知识分类ID为空");
        categoryService.updateById(category);
    }
}
