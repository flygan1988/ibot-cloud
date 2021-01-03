package com.taiping.ibot.server.knowledge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taiping.ibot.server.knowledge.entity.qa.Qa;
import org.apache.ibatis.annotations.Param;

public interface QaMapper extends BaseMapper<Qa> {

    /**
     * 查找QA详细信息
     * @param qa
     * @return
     */
    IPage<Qa> findQaDetailPage(Page page, @Param("qa") Qa qa);

    /**
     * 按照ID查找qa详细信息
     * @param qid
     * @return
     */
    Qa findQaDetailById(@Param("qid") Long qid);
}
