package com.taiping.ibot.server.knowledge.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.server.knowledge.entity.qa.QaCategory;

import java.util.List;

public interface IQaCategoryService extends IService<QaCategory> {

    /**
     * 获取分类列表
     * @param category
     * @return
     */
    List<QaCategory> findQaCategoryList(QaCategory category);

    /**
     * 删除分类
     * @param ids
     */
    void deleteQaCategory(List<String> ids);

    /**
     * 根据Kid删除分类
     * @param kids
     */
    void deleteQaCategoryByKid(String[] kids);

    /**
     * 按ID获取分类实体
     * @param id
     * @return
     */
    QaCategory findQaCategoryById(Long id);

}
