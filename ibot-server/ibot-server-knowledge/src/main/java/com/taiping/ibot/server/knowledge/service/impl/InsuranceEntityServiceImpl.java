package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.server.knowledge.entity.setting.InsuranceEntity;
import com.taiping.ibot.server.knowledge.entity.setting.enums.OperationType;
import com.taiping.ibot.server.knowledge.mapper.InsuranceEntityMapper;
import com.taiping.ibot.server.knowledge.service.IInsuranceEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fei
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InsuranceEntityServiceImpl extends ServiceImpl<InsuranceEntityMapper, InsuranceEntity> implements IInsuranceEntityService {
    @Override
    public IPage<InsuranceEntity> getEntityByCondition(String field, String value, int pageNum, int pageSize) {
        Page<InsuranceEntity> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectPage(page, new QueryWrapper<InsuranceEntity>()
                .ne("status", "del")
                .like(field, value));
    }

    @Override
    public IPage<InsuranceEntity> getEntities(int pageNum, int pageSize) {
        Page<InsuranceEntity> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectPage(page, new QueryWrapper<InsuranceEntity>()
                .ne("status", OperationType.DELETE.getOperationType()).orderByDesc("id"));
    }

    @Override
    @Transactional
    public void addEntity(InsuranceEntity entity) {
        InsuranceEntity tmp = this.baseMapper.selectOne(new QueryWrapper<InsuranceEntity>().eq("short_name", entity.getShortName()));
        if (tmp == null) {
            entity.setStatus("add");
            this.save(entity);
        }else {
            entity.setId(tmp.getId());
            entity.setStatus("update");
            updateById(entity);
        }

    }

    @Override
    @Transactional
    public void updateEntity(InsuranceEntity entity) {
        entity.setStatus(OperationType.UPDATE.getOperationType());
        this.updateById(entity);
    }

    @Override
    @Transactional
    public void deleteEntities(List<String> idList) {
        this.baseMapper.removeEntities(idList);
    }
}
