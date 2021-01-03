package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.server.knowledge.entity.qa.QaCategory;
import com.taiping.ibot.server.knowledge.mapper.QaCategoryMapper;
import com.taiping.ibot.server.knowledge.service.IQaCategoryService;
import com.taiping.ibot.server.knowledge.service.IQaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QaCategoryImpl extends ServiceImpl<QaCategoryMapper, QaCategory> implements IQaCategoryService {

    @Autowired
    private IQaCategoryService qaCategoryService;

    @Autowired
    private IQaService qaService;

    @Override
    public List<QaCategory> findQaCategoryList(QaCategory category) {
        return this.baseMapper.findCategoryList(category);
    }

    @Override
    public void deleteQaCategory(List<String> ids) {
        this.delete(ids);
    }

    @Override
    public void deleteQaCategoryByKid(String[] kids) {
        List<QaCategory> categoryList = this.baseMapper.selectList(new QueryWrapper<QaCategory>().in("k_id", Arrays.asList(kids)));
        if (categoryList == null || categoryList.isEmpty()) return;
        List<String> cidList = new ArrayList<>();
        categoryList.forEach(qc -> {
            cidList.add(String.valueOf(qc.getId()));
        });
        deleteQaCategory(cidList);
    }

    @Override
    public QaCategory findQaCategoryById(Long id) {
        return this.getById(id);
    }

    private void delete(List<String> categoryIds) {
        removeByIds(categoryIds);
//        qaService.remove(new QueryWrapper<Qa>().lambda().in(Qa::getCategoryId, categoryIds));
        qaService.deleteByCid(categoryIds);
        LambdaQueryWrapper<QaCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(QaCategory::getPid, categoryIds);
        List<QaCategory> categories = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(categories)) {
            List<String> cateIdList = new ArrayList<>();
            categories.forEach(c -> cateIdList.add(String.valueOf(c.getId())));
            this.delete(cateIdList);
        }
    }
}
