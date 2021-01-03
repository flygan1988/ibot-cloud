package com.taiping.ibot.server.knowledge.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.server.knowledge.entity.setting.FeatureWord;

import java.util.List;

public interface IFeatureWordService extends IService<FeatureWord> {
    IPage<FeatureWord> getFeatureWordByCondition(String field, String value, int pageNum, int pageSize);

    IPage<FeatureWord> getFeatureWords(int pageNum, int pageSize);

    void addFeatureWord(FeatureWord featureWord);

    void updateFeatureWord(FeatureWord featureWord);

    void deleteFeatureWords(List<String> idList);

}
