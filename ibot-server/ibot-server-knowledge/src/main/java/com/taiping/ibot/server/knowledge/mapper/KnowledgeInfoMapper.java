package com.taiping.ibot.server.knowledge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taiping.ibot.server.knowledge.entity.qa.KnowledgeInfo;
import org.apache.ibatis.annotations.Param;

public interface KnowledgeInfoMapper extends BaseMapper<KnowledgeInfo> {

    IPage<KnowledgeInfo> findKnowledgeInfoDetailPage(Page page, @Param("knowledgeInfo") KnowledgeInfo knowledgeInfo);

}
