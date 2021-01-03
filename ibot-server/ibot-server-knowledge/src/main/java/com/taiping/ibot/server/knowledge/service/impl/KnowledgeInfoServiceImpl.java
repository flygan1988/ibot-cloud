package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.common.entity.IBotConstant;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.utils.SortUtil;
import com.taiping.ibot.server.knowledge.entity.qa.KnowledgeInfo;
import com.taiping.ibot.server.knowledge.mapper.KnowledgeInfoMapper;
import com.taiping.ibot.server.knowledge.service.IKnowledgeInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KnowledgeInfoServiceImpl extends ServiceImpl<KnowledgeInfoMapper, KnowledgeInfo> implements IKnowledgeInfoService {

//    @Autowired
//    private IQaCategoryService qaCategoryService;
//
//    @Autowired
//    private IQaService qaService;
//
//    @Autowired
//    private IBotKnowledgeService botKnowledgeService;

    @Override
    public IPage<KnowledgeInfo> findKnowledgeInfoDetail(KnowledgeInfo knowledgeInfo, QueryRequest request) {
        Page<KnowledgeInfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "id", IBotConstant.ORDER_ASC, false);
        return this.baseMapper.findKnowledgeInfoDetailPage(page, knowledgeInfo);
    }

    @Override
    @Transactional
    public void deleteKnowledgeInfo(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
        //删除机器人知识关系
//        botKnowledgeService.deleteBotKnowledgeByKId(list);
        //删除该知识点下分类
//        qaCategoryService.deleteQaCategoryByKid(ids);
    }

}
