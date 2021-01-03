package com.taiping.ibot.server.knowledge.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.server.knowledge.entity.statistic.ThumbUpDownDto;
import com.taiping.ibot.server.knowledge.entity.statistic.UserRecord;

import java.util.List;
import java.util.Map;

public interface IStatisticService extends IService<UserRecord> {

    IPage<UserRecord> findUserRecordDetailPage(UserRecord record, QueryRequest request);

    Map<String, Object> getUserRecordCountByDay(List<String> dateList, Long botId);

    Map<String, Object> getUserRecordCountByMonth(List<String> dateList, Long botId);

    Map<String, Object> getUserCountByDay(List<String> dateList, Long botId);

    Map<String, Object> getUserCountByMonth(List<String> dateList, Long botId);

    Map<String, String> getRecallByDay(List<String> dateList, Long botId);

    Map<String, String> getRecallByMonth(List<String> dateList, Long botId);

    Map<String, Object> getHotUser(String startDate, String endDate, Integer top, Long botId);

    Map<String, Object> getBotHandleCount(String startDate, String endDate, Long botId);

    IPage<UserRecord> getThumbUp(QueryRequest queryRequest, Long botId);

    IPage<UserRecord> getThumbDown(QueryRequest queryRequest, Long botId);

    IPage<UserRecord> getUnknownUserQuestion(QueryRequest queryRequest, Long botId);

    Map<String, Object> getRecallQuestionByDay(List<String> dateList, Long botId);

    Map<String, Object> getRecallQuestionByMonth(List<String> dateList, Long botId);

    IPage<ThumbUpDownDto> statisticThumbUp(Integer pageNo, Integer pageSize, Long botId);

    IPage<ThumbUpDownDto> statisticThumbDown(Integer pageNo, Integer pageSize, Long botId);


}
