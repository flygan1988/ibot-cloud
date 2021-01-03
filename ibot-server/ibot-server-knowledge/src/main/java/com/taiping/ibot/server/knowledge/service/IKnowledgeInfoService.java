package com.taiping.ibot.server.knowledge.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.server.knowledge.entity.qa.KnowledgeInfo;

public interface IKnowledgeInfoService extends IService<KnowledgeInfo> {

    /**
     * 查找知识库详细信息
     * @param knowledgeInfo
     * @param request
     * @return
     */
    IPage<KnowledgeInfo> findKnowledgeInfoDetail(KnowledgeInfo knowledgeInfo, QueryRequest request);

    /**
     * 删除知识库
     * @param ids
     */
    void deleteKnowledgeInfo(String[] ids);

}
