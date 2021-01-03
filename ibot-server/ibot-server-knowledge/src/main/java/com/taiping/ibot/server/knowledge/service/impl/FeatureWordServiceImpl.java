package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.server.knowledge.entity.setting.FeatureWord;
import com.taiping.ibot.server.knowledge.entity.setting.enums.OperationType;
import com.taiping.ibot.server.knowledge.mapper.FeatureWordMapper;
import com.taiping.ibot.server.knowledge.service.IFeatureWordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fei
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FeatureWordServiceImpl extends ServiceImpl<FeatureWordMapper, FeatureWord> implements IFeatureWordService {
    @Override
    public IPage<FeatureWord> getFeatureWordByCondition(String field, String value, int pageNum, int pageSize) {
        Page<FeatureWord> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectPage(page, new QueryWrapper<FeatureWord>()
                .ne("status", "del")
                .like(field, value));
    }

    @Override
    public IPage<FeatureWord> getFeatureWords(int pageNum, int pageSize) {
        Page<FeatureWord> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectPage(page, new QueryWrapper<FeatureWord>()
                .ne("status", OperationType.DELETE.getOperationType()).orderByDesc("id"));
    }

    @Override
    @Transactional
    public void addFeatureWord(FeatureWord featureWord) {
        FeatureWord tmp = this.baseMapper.selectOne(new QueryWrapper<FeatureWord>().eq("feature_word", featureWord.getFeatureWord()));
        if (tmp == null) {
            featureWord.setStatus("add");
            this.save(featureWord);
        }else {
            featureWord.setId(tmp.getId());
            featureWord.setStatus("update");
            updateById(featureWord);
        }

    }

    @Override
    @Transactional
    public void updateFeatureWord(FeatureWord featureWord) {
        featureWord.setStatus(OperationType.UPDATE.getOperationType());
        this.updateById(featureWord);
    }

    @Override
    @Transactional
    public void deleteFeatureWords(List<String> idList) {
        this.baseMapper.removeFeatureWords(idList);
    }
}
