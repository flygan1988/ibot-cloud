package com.taiping.ibot.server.knowledge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taiping.ibot.server.knowledge.entity.setting.FeatureWord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeatureWordMapper extends BaseMapper<FeatureWord> {
    void removeFeatureWords(@Param("idList") List<String> idList);
}
