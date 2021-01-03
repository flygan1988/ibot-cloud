package com.taiping.ibot.server.knowledge.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.server.knowledge.entity.setting.InsuranceEntity;

import java.util.List;

public interface IInsuranceEntityService extends IService<InsuranceEntity> {

    IPage<InsuranceEntity> getEntityByCondition(String field, String value, int pageNum, int pageSize);

    IPage<InsuranceEntity> getEntities(int pageNum, int pageSize);

    void addEntity(InsuranceEntity entity);

    void updateEntity(InsuranceEntity entity);

    void deleteEntities(List<String> idList);
}
