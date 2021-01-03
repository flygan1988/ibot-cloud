package com.taiping.ibot.server.knowledge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taiping.ibot.server.knowledge.entity.qa.QaCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QaCategoryMapper extends BaseMapper<QaCategory> {

    List<QaCategory> findCategoryList(@Param("category") QaCategory category);
}
