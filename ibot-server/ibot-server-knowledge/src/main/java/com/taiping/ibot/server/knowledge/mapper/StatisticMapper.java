package com.taiping.ibot.server.knowledge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taiping.ibot.server.knowledge.entity.statistic.ThumbUpDownDto;
import com.taiping.ibot.server.knowledge.entity.statistic.UserRecord;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Fei
 */
public interface StatisticMapper extends BaseMapper<UserRecord> {

    @MapKey("date")
    Map<String, Object> selectUserRecordCountByDay(@Param("dateList") List<String> dateList, @Param("botId") Long botId);

    @MapKey("date")
    Map<String, Object> selectUserCountByDay(@Param("dateList") List<String> dateList, @Param("botId") Long botId);

    @MapKey("date")
    Map<String, Object> selectUserRecordCountByMonth(@Param("dateList") List<String> dateList, @Param("botId") Long botId);

    @MapKey("date")
    Map<String, Object> selectUserCountByMonth(@Param("dateList") List<String> dateList, @Param("botId") Long botId);

    @MapKey("date")
    Map<String, Object> selectRecallQuestionCountByDay(@Param("dateList") List<String> dateList, @Param("botId") Long botId);

    @MapKey("date")
    Map<String, Object> selectRecallQuestionCountByMonth(@Param("dateList") List<String> dateList, @Param("botId") Long botId);

    @MapKey("user")
    Map<String, Object> selectHotUser(@Param("startDate") String startDate,
                                      @Param("endDate") String endDate,
                                      @Param("top") Integer top,
                                      @Param("botId") Long botId);

    @MapKey("bot")
    Map<String, Object> selectBotHandleCount(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("botId") Long botId);

    IPage<ThumbUpDownDto> statisticThumbUp(Page page, @Param("botId") Long botId);

    IPage<ThumbUpDownDto> statisticThumbDown(Page page, @Param("botId") Long botId);

    IPage<UserRecord> selectUserRecordDetailPage(Page page, @Param("record") UserRecord record);
}
