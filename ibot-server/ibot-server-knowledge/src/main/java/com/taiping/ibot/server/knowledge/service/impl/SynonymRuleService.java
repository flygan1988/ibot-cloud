package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.server.knowledge.entity.setting.DynamicSynonymRule;
import com.taiping.ibot.server.knowledge.mapper.SynonymMapper;
import com.taiping.ibot.server.knowledge.service.ISynonymService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author Fei
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SynonymRuleService extends ServiceImpl<SynonymMapper, DynamicSynonymRule> implements ISynonymService {

    @Override
    public IPage<DynamicSynonymRule> selectSynonymRule(int pageNum, int pageSize) {
        Page<DynamicSynonymRule> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectPage(page, new QueryWrapper<DynamicSynonymRule>().orderByDesc("id"));
    }

    @Override
    @Transactional
    public void createSynonymRule(DynamicSynonymRule dynamicSynonymRule) {
        this.save(dynamicSynonymRule);
    }

    @Override
    @Transactional
    public void updateSynonymRule(DynamicSynonymRule dynamicSynonymRule) {
        this.updateById(dynamicSynonymRule);
    }

    @Override
    @Transactional
    public void deleteSynonymRule(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        this.baseMapper.deleteBatchIds(idList);
    }

    @Override
    public IPage<DynamicSynonymRule> searchSynonymRule(int pageNum, int pageSize, String query) {
        Page<DynamicSynonymRule> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectPage(page, new QueryWrapper<DynamicSynonymRule>().like("rule", query));
    }
}
