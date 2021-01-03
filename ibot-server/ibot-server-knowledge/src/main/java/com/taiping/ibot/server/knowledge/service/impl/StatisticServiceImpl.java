package com.taiping.ibot.server.knowledge.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taiping.ibot.common.entity.IBotConstant;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.utils.SortUtil;
import com.taiping.ibot.server.knowledge.entity.statistic.ThumbUpDownDto;
import com.taiping.ibot.server.knowledge.entity.statistic.UserRecord;
import com.taiping.ibot.server.knowledge.mapper.StatisticMapper;
import com.taiping.ibot.server.knowledge.service.IStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fei
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StatisticServiceImpl extends ServiceImpl<StatisticMapper, UserRecord> implements IStatisticService {

    /**
     * 分页获取用户提问历史记录
     * @param record
     * @param request
     * @return
     */
    @Override
    public IPage<UserRecord> findUserRecordDetailPage(UserRecord record, QueryRequest request) {
        Page<UserRecord> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "send_time", IBotConstant.ORDER_DESC, false);
        return this.baseMapper.selectUserRecordDetailPage(page, record);
    }


    /**
     * 按天获取提问数量
     * @param dateList 日期数组
     * @return
     */
    @Override
    public Map<String, Object> getUserRecordCountByDay(List<String> dateList, Long botId) {
        Map<String, Object> ret = this.baseMapper.selectUserRecordCountByDay(dateList, botId);
        for (String date : dateList) {
            if (!ret.containsKey(date)) {
                Map<String, Object> data = new HashMap<>();
                data.put("date", date);
                data.put("count", 0L);
                ret.put(date, data);
            }
        }
        return ret;
    }

    /**
     * 按月获取提问数量
     * @param dateList 月份数组
     * @return
     */
    @Override
    public Map<String, Object> getUserRecordCountByMonth(List<String> dateList, Long botId) {
        Map<String, Object> ret = this.baseMapper.selectUserRecordCountByMonth(dateList, botId);
        for (String date : dateList) {
            if (!ret.containsKey(date)) {
                Map<String, Object> data = new HashMap<>();
                data.put("date", date);
                data.put("count", 0L);
                ret.put(date, data);
            }
        }
        return ret;
    }

    /**
     * 按天获取机器人用户访问人数
     * @param dateList
     * @return
     */
    @Override
    public Map<String, Object> getUserCountByDay(List<String> dateList, Long botId) {
        Map<String, Object> ret = this.baseMapper.selectUserCountByDay(dateList, botId);
        for (String date : dateList) {
            if (!ret.containsKey(date)) {
                Map<String, Object> data = new HashMap<>();
                data.put("date", date);
                data.put("count", 0L);
                ret.put(date, data);
            }
        }
        return ret;
    }

    /**
     * 按月获取机器人用户访问人数
     * @param dateList
     * @return
     */
    @Override
    public Map<String, Object> getUserCountByMonth(List<String> dateList, Long botId) {
        Map<String, Object> ret = this.baseMapper.selectUserCountByMonth(dateList, botId);
        for (String date : dateList) {
            if (!ret.containsKey(date)) {
                Map<String, Object> data = new HashMap<>();
                data.put("date", date);
                data.put("count", 0L);
                ret.put(date, data);
            }
        }
        return ret;
    }

    /**
     * 按天获取召回率
     * @param dateList
     * @return
     */
    @Override
    public Map<String, String> getRecallByDay(List<String> dateList, Long botId) {
        Map<String, String> resultMap = new HashMap<>();
        Map<String, Object> userRecordCountMap = getUserRecordCountByDay(dateList, botId);
        Map<String, Object> recallQuestionCountMap = getRecallQuestionByDay(dateList, botId);
        DecimalFormat df = new DecimalFormat("0.00");
        for (Map.Entry<String, Object> entry : userRecordCountMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Long> recordCountMap = (Map<String, Long>)entry.getValue();
            Map<String, Long> recallQuestinMap = (Map<String, Long>)recallQuestionCountMap.get(key);
            if (recordCountMap.get("count") == 0L) {
                resultMap.put(key, "0.00");
                continue;
            }
            String recall = df.format((float)recallQuestinMap.get("count") / recordCountMap.get("count"));
            resultMap.put(key, recall);
        }
        return resultMap;
    }

    /**
     * 按月获取召回率
     * @param dateList
     * @return
     */
    @Override
    public Map<String, String> getRecallByMonth(List<String> dateList, Long botId) {
        Map<String, String> resultMap = new HashMap<>();
        Map<String, Object> userRecordCountMap = getUserRecordCountByMonth(dateList, botId);
        Map<String, Object> recallQuestionCountMap = getRecallQuestionByMonth(dateList, botId);
        DecimalFormat df = new DecimalFormat("0.00");
        for (Map.Entry<String, Object> entry : userRecordCountMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Long> recordCountMap = (Map<String, Long>)entry.getValue();
            Map<String, Long> recallQuestinMap = (Map<String, Long>)recallQuestionCountMap.get(key);
            if (recordCountMap.get("count") == 0L) {
                resultMap.put(key, "0.00");
                continue;
            }
            String recall = df.format((float)recallQuestinMap.get("count") / recordCountMap.get("count"));
            resultMap.put(key, recall);
        }
        return resultMap;
    }

    /**
     * 一段时间区间用户提问数量
     * @param startDate
     * @return 用户id和提问数量的字典
     */
    @Override
    public Map<String, Object> getHotUser(String startDate, String endDate, Integer top, Long botId) {
        return this.baseMapper.selectHotUser(startDate, endDate, top, botId);
    }

    /**
     * 按照时间区间获取各类型机器人处理消息量
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public Map<String, Object> getBotHandleCount(String startDate, String endDate, Long botId) {
        return this.baseMapper.selectBotHandleCount(startDate, endDate, botId);
    }

    /**
     * 分页获取用户点赞的提问记录
     * @param queryRequest
     * @return
     */
    @Override
    public IPage<UserRecord> getThumbUp(QueryRequest queryRequest, Long botId) {
        return this.baseMapper.selectPage(new Page<UserRecord>(queryRequest.getPageNum(), queryRequest.getPageSize()),
                new QueryWrapper<UserRecord>()
                        .gt("thumb_up", 0)
                        .orderByDesc("send_time"));
    }

    /**
     * 分页获取用户点踩的提问记录
     * @param queryRequest
     * @return
     */
    @Override
    public IPage<UserRecord> getThumbDown(QueryRequest queryRequest, Long botId) {
        return this.baseMapper.selectPage(new Page<UserRecord>(queryRequest.getPageNum(), queryRequest.getPageSize()),
                new QueryWrapper<UserRecord>()
                        .gt("thumb_down", 0)
                        .orderByDesc("send_time"));
    }

    /**
     * 分页获取未识别问题记录
     * @param queryRequest
     * @return
     */
    @Override
    public IPage<UserRecord> getUnknownUserQuestion(QueryRequest queryRequest, Long botId) {
        return this.baseMapper.selectPage(new Page<UserRecord>(queryRequest.getPageNum(), queryRequest.getPageSize()),
                new QueryWrapper<UserRecord>()
                .eq("bot_type", '3')
                .eq("bot_id", botId)
                .orderByDesc("send_time"));
    }

    /**
     * 按天获取召回问题数
     * @param dateList
     * @return
     */
    @Override
    public Map<String, Object> getRecallQuestionByDay(List<String> dateList, Long botId) {
        Map<String, Object> ret = this.baseMapper.selectRecallQuestionCountByDay(dateList, botId);
        for (String date : dateList) {
            if (!ret.containsKey(date)) {
                Map<String, Object> data = new HashMap<>();
                data.put("date", date);
                data.put("count", 0L);
                ret.put(date, data);
            }
        }
        return ret;
    }

    /**
     * 按月获取召回问题数
     * @param dateList
     * @return
     */
    @Override
    public Map<String, Object> getRecallQuestionByMonth(List<String> dateList, Long botId) {
        Map<String, Object> ret = this.baseMapper.selectRecallQuestionCountByMonth(dateList, botId);
        for (String date : dateList) {
            if (!ret.containsKey(date)) {
                Map<String, Object> data = new HashMap<>();
                data.put("date", date);
                data.put("count", 0L);
                ret.put(date, data);
            }
        }
        return ret;
    }

    /**
     * 统计点赞QA
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ThumbUpDownDto> statisticThumbUp(Integer pageNo, Integer pageSize, Long botId) {
        Page<ThumbUpDownDto> page = new Page<>(pageNo, pageSize);
        return this.baseMapper.statisticThumbUp(page, botId);
    }

    /**
     * 统计点踩QA
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ThumbUpDownDto> statisticThumbDown(Integer pageNo, Integer pageSize, Long botId) {
        Page<ThumbUpDownDto> page = new Page<>(pageNo, pageSize);
        return this.baseMapper.statisticThumbDown(page, botId);
    }

}
