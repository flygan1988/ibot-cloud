package com.taiping.ibot.server.knowledge.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.server.knowledge.entity.setting.DynamicSynonymRule;

public interface ISynonymService extends IService<DynamicSynonymRule> {

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<DynamicSynonymRule> selectSynonymRule(int pageNum, int pageSize);

    /**
     * 新增同义词规则
     * @param dynamicSynonymRule
     */
    void createSynonymRule(DynamicSynonymRule dynamicSynonymRule);

    /**
     * 更新同义词规则
     * @param dynamicSynonymRule
     */
    void updateSynonymRule(DynamicSynonymRule dynamicSynonymRule);

    /**
     * 删除同义词规则
     * @param ids
     */
    void deleteSynonymRule(String[] ids);

    /**
     * 检索同义词
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<DynamicSynonymRule> searchSynonymRule(int pageNum, int pageSize, String query);
}
