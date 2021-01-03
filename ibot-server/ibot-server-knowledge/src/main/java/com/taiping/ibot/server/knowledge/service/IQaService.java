package com.taiping.ibot.server.knowledge.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.server.knowledge.entity.qa.Qa;

import java.util.List;

public interface IQaService extends IService<Qa> {

    IPage<Qa> findQaDetailPage(Qa qa, QueryRequest request);

    Qa findQaDetailById(Long id);

    void deleteQa(String[] qids);

    void deleteByCid(List<String> cids);

    void createQa(Qa qa);

    void updateQa(Qa qa);

    void importQa(List<Qa> list, Long kid);

    List<Qa> exportQa(Qa qa);

}
