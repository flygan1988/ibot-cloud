package com.taiping.ibot.server.knowledge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taiping.ibot.server.knowledge.entity.setting.InsuranceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InsuranceEntityMapper extends BaseMapper<InsuranceEntity> {
    void removeEntities(@Param("idList") List<String> idList);
}
