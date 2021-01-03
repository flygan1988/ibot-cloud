package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.common.entity.CurrentUser;
import com.taiping.ibot.common.entity.IBotConstant;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.utils.IBotUtil;
import com.taiping.ibot.common.utils.SortUtil;
import com.taiping.ibot.server.knowledge.entity.qa.Qa;
import com.taiping.ibot.server.knowledge.entity.qa.QaCategory;
import com.taiping.ibot.server.knowledge.mapper.QaMapper;
import com.taiping.ibot.server.knowledge.repository.es.QaEsRepository;
import com.taiping.ibot.server.knowledge.service.IQaCategoryService;
import com.taiping.ibot.server.knowledge.service.IQaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QaServiceImpl extends ServiceImpl<QaMapper, Qa> implements IQaService {

    @Autowired
    private IQaCategoryService categoryService;

    @Autowired
    @Lazy
    private QaEsRepository qaEsRepository;

    @Override
    public IPage<Qa> findQaDetailPage(Qa qa, QueryRequest request) {
        Page<Qa> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "id", IBotConstant.ORDER_ASC, false);
        return this.baseMapper.findQaDetailPage(page, qa);
    }

    @Override
    public Qa findQaDetailById(Long id) {
        return this.baseMapper.findQaDetailById(id);
    }


    @Override
    @Transactional
    public void deleteQa(String[] qids) {
        List<String> list = Arrays.asList(qids);
//        removeByIds(list);
//        remove(new QueryWrapper<Qa>().lambda().in(Qa::getPid, list));
        delete(list);
    }

    @Override
    @Transactional
    public void deleteByCid(List<String> cids) {
        List<Qa> qaList = this.baseMapper.selectList(new QueryWrapper<Qa>().lambda().in(Qa::getCategoryId, cids));
        this.remove(new QueryWrapper<Qa>().lambda().in(Qa::getCategoryId, cids));
        List<String> idList = new ArrayList<>(qaList.size());
        qaList.forEach(qa -> {
            idList.add(String.valueOf(qa.getId()));
        });
        if (idList == null || idList.isEmpty()) {
            return;
        }
        qaEsRepository.deleteBath(idList);
    }

    @Override
    @Transactional
    public void createQa(Qa qa) {
        save(qa);
        //存索引
        qaEsRepository.addOne(qa);
        if (qa.getSimilarQuestion() == null || qa.getSimilarQuestion().isEmpty()) return;
        String[] simQuestions = qa.getSimilarQuestion().split("\\|\\|");
        List<Qa> simQaList = constructQa(qa, simQuestions);
        //保存相似问题
        saveBatch(simQaList);
        //存索引
        qaEsRepository.addBatch(simQaList);
    }

    @Override
    @Transactional
    public void updateQa(Qa qa) {
        updateById(qa);
        if (qa.getSimilarQid() != null && !qa.getSimilarQid().isEmpty()) {
            String[] simIds = qa.getSimilarQid().split("\\|\\|");
            //删除原有相似问题
            removeByIds(Arrays.asList(simIds));
            qaEsRepository.deleteBath(Arrays.asList(simIds));
        }
        if (qa.getSimilarQuestion() != null && !qa.getSimilarQuestion().isEmpty()) {
            //重新插入新的相似问题
            String[] simQuestions = qa.getSimilarQuestion().split("\\|\\|");
            List<Qa> simList = constructQa(qa, simQuestions);
            saveBatch(simList);
            qaEsRepository.addBatch(simList);
        }
        qa = this.baseMapper.findQaDetailById(qa.getId());
        qaEsRepository.update(qa);
    }

    @Override
    public void importQa(List<Qa> list, Long kid) {
        CurrentUser currentUser = IBotUtil.getCurrentUser();
        for (Qa qa : list) {
            String categoryName = qa.getCategoryName();
            if (categoryName == null || categoryName.isEmpty()) continue;
            String[] categories = categoryName.split("\\/");
            Long cId = saveCategory(categories, kid);
            qa.setPid(0L);
            qa.setCategoryId(cId);
            qa.setKid(kid);
            qa.setStatus(true);
            qa.setUserId(currentUser.getUserId());
            createQa(qa);
        }
    }

//    @Override
//    public void importQa(List<ExcelKnowledgeImport> list, Long kid) {
//        User currentUser = FebsUtil.getCurrentUser();
//        for (ExcelKnowledgeImport knowledge : list) {
//            String categoryName = knowledge.getCategoryName();
//            if (categoryName == null || categoryName.isEmpty()) continue;
//            String[] categories = categoryName.split("\\/");
//            Long cId = saveCategory(categories, kid);
//            String standardQuestion = knowledge.getStandardQuestion();
//            if (standardQuestion == null || standardQuestion.isEmpty()) continue;
//            Qa qa = new Qa();
//            qa.setPid(0L);
//            qa.setQuestion(knowledge.getStandardQuestion());
//            qa.setSimilarQuestion(knowledge.getSimilarQuestion());
//            qa.setVideoUrl(knowledge.getVideoUrl());
//            qa.setPicUrl(knowledge.getPicUrl());
//            qa.setAnswer(knowledge.getAnswer());
//            qa.setKid(kid);
//            qa.setCategoryId(cId);
//            qa.setStatus(true);
//            qa.setUserId(currentUser.getUserId());
//            qa.setStartDate(knowledge.getStartDate());
//            qa.setEndDate(knowledge.getEndDate());
//            createQa(qa);
//        }
//    }

    @Override
    public List<Qa> exportQa(Qa qa) {
        QueryRequest request = new QueryRequest();
        request.setPageSize(50000);
        IPage<Qa> page = findQaDetailPage(qa, request);
        List<Qa> list = page.getRecords();
        list.forEach(entity -> {
            String wholeCategoryName = getWholeCategoryName(entity.getCategoryId());
            entity.setCategoryName(wholeCategoryName);
        });
        return list;
    }

    private String getWholeCategoryName(Long categoryId) {
        String wholeCategoryName = "";
        QaCategory category = categoryService.findQaCategoryById(categoryId);
        if (category.getPid() == 0L) {
            wholeCategoryName = category.getName();
        }else {
            wholeCategoryName = getWholeCategoryName(category.getPid()) + "/" + category.getName();
        }
        return wholeCategoryName;
    }

    private QaCategory inSubCategories(String categoryName, List<QaCategory> subCategories) {
        if (subCategories == null || subCategories.isEmpty()) return null;
        for (QaCategory category : subCategories) {
            if (category.getName().equalsIgnoreCase(categoryName)) return category;
        }
        return null;
    }

    private Long saveCategory(String[] categories, Long kid) {
        Long pid = 0L;
        QaCategory queryEntity = new QaCategory();
        List<QaCategory> subCategories = null;
        for (int i = 0; i < categories.length; i++) {
            queryEntity.setPid(pid);
            queryEntity.setKid(kid);
            subCategories = categoryService.findQaCategoryList(queryEntity);
            QaCategory category = inSubCategories(categories[i], subCategories);
            if (category == null) {
                category = new QaCategory();
                category.setPid(pid);
                category.setName(categories[i]);
                category.setKid(kid);
                categoryService.save(category);
            }
            pid = category.getId();
        }
        return pid;
    }

    private List<Qa> constructQa(Qa qa, String[] simQuestions) {
        List<Qa> simQaList = new ArrayList<>();
        for (String simQuestion : simQuestions) {
            Qa simQa = new Qa();
            BeanUtils.copyProperties(qa, simQa);
            simQa.setPid(qa.getId());
            simQa.setId(null);
            simQa.setQuestion(simQuestion);
            simQaList.add(simQa);
        }
        return simQaList;
    }

    private void delete(List<String> ids) {
        removeByIds(ids);
        qaEsRepository.deleteBath(ids);
        LambdaQueryWrapper<Qa> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Qa::getPid, ids);
        List<Qa> kQuestions = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(kQuestions)) {
            List<String> kQuestionIdList = new ArrayList<>();
            kQuestions.forEach(c -> kQuestionIdList.add(String.valueOf(c.getId())));
            this.delete(kQuestionIdList);
        }
    }
}
