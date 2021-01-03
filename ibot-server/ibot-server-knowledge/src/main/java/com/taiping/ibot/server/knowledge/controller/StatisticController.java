package com.taiping.ibot.server.knowledge.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.utils.DateUtil;
import com.taiping.ibot.server.knowledge.entity.statistic.ThumbUpDownDto;
import com.taiping.ibot.server.knowledge.entity.statistic.UserRecord;
import com.taiping.ibot.server.knowledge.service.IStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.taiping.ibot.common.utils.IBotUtil.getDataTable;

/**
 * @author Fei
 */
@Slf4j
@RestController
@Validated
@RequestMapping("statistic")
public class StatisticController {

    @Autowired
    private IStatisticService statisticService;

    @GetMapping("selectUserRecordCount")
    public IBotResponse selectUserRecordCount(@RequestParam("startDate") String startDate,
                                              @RequestParam("endDate") String endDate,
                                              @RequestParam("dateType") String dateType,
                                              @RequestParam(value = "botId", required = false) Long botId) throws ParseException {
        List<String> dateList = DateUtil.findDateList(startDate, endDate, dateType);
        Map<String, Object> data = null;
        if ("day".equalsIgnoreCase(dateType)) { //按天获取提问数量
            data = statisticService.getUserRecordCountByDay(dateList, botId);
        }else if ("month".equalsIgnoreCase(dateType)) { //按月获提问数量
            data = statisticService.getUserRecordCountByMonth(dateList, botId);
        }else {
            return new IBotResponse().message("请提供正确的日期类型");
        }
        return new IBotResponse().data(data);
    }

    @GetMapping("selectUserCount")
    public IBotResponse selectUserCount(@RequestParam("startDate") String startDate,
                                        @RequestParam("endDate") String endDate,
                                        @RequestParam("dateType") String dateType,
                                        @RequestParam(value = "botId", required = false) Long botId) throws ParseException {
        List<String> dateList = DateUtil.findDateList(startDate, endDate, dateType);
        Map<String, Object> data = null;
        if ("day".equalsIgnoreCase(dateType)) { //按天获取用户数量
            data = statisticService.getUserCountByDay(dateList, botId);
        }else if ("month".equalsIgnoreCase(dateType)) { //按月获用户数量
            data = statisticService.getUserCountByMonth(dateList, botId);
        }else {
            return new IBotResponse().message("请提供正确的日期类型");
        }
        return new IBotResponse().data(data);
    }

    @GetMapping("selectRecallQuestionCount")
    public IBotResponse selectRecallQuestionCount(@RequestParam("startDate") String startDate,
                                                  @RequestParam("endDate") String endDate,
                                                  @RequestParam("dateType") String dateType,
                                                  @RequestParam(value = "botId", required = false) Long botId) throws ParseException {
        List<String> dateList = DateUtil.findDateList(startDate, endDate, dateType);
        Map<String, Object> data = null;
        if ("day".equalsIgnoreCase(dateType)) { //按天获取提问数量
            data = statisticService.getRecallQuestionByDay(dateList, botId);
        }else if ("month".equalsIgnoreCase(dateType)) { //按月获提问数量
            data = statisticService.getRecallQuestionByMonth(dateList, botId);
        }else {
            return new IBotResponse().message("请提供正确的日期类型");
        }
        return new IBotResponse().data(data);
    }


    @GetMapping("selectRecall")
    public IBotResponse selectRecall(@RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate,
                                     @RequestParam("dateType") String dateType,
                                     @RequestParam(value = "botId", required = false) Long botId) throws ParseException {
        List<String> dateList = DateUtil.findDateList(startDate, endDate, dateType);
        Map<String, String> data = null;
        if ("day".equalsIgnoreCase(dateType)) { //按天获取提问数量
            data = statisticService.getRecallByDay(dateList, botId);
        }else if ("month".equalsIgnoreCase(dateType)) { //按月获提问数量
            data = statisticService.getRecallByMonth(dateList, botId);
        }else {
            return new IBotResponse().message("请提供正确的日期类型");
        }
        return new IBotResponse().data(data);
    }

    @GetMapping("selectUserRecord")
    public IBotResponse getUserRecord(UserRecord record,
                                      QueryRequest queryRequest) {
        IPage<UserRecord> page = statisticService.findUserRecordDetailPage(record, queryRequest);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

//    @GetMapping("selectUserRecordBySenderId")
//    @ControllerEndpoint(exceptionMessage = "按用户id获取用户问题失败")
//    public FebsResponse getUserRecordBySenderId(@RequestParam("senderId") String senderId,
//                                                QueryRequest queryRequest) {
//        IPage<UserRecord> page = statisticService.getUserRecordBySenderId(senderId, queryRequest);
//        Map<String, Object> data = getDataTable(page);
//        return new FebsResponse().success().data(data);
//    }

//    @GetMapping("selectThumbUpOrDown")
//    @ControllerEndpoint(exceptionMessage = "获取用户点赞\\踩问题失败")
//    public FebsResponse getThumbUpOrDown(QueryRequest queryRequest,
//                                         @RequestParam("type") Integer type,
//                                         @RequestParam(value = "botId", required = false) Long botId) {
//        IPage<UserRecord> page = null;
//        if (type == 0) { //获取点赞用户问题记录
//            page = statisticService.getThumbUp(queryRequest, botId);
//        }else if (type == 1) { //获取点踩用户问题记录
//            page = statisticService.getThumbDown(queryRequest, botId);
//        }else {
//            return new FebsResponse().message("请提供正确获取类型");
//        }
//        Map<String, Object> data = getDataTable(page);
//        return new FebsResponse().success().data(data);
//    }

    @GetMapping("selectUnknownUserQuestion")
    public IBotResponse getUnknownUserQuestion(QueryRequest queryRequest,
                                               @RequestParam(value = "botId", required = false) Long botId) {
        IPage<UserRecord> page = statisticService.getUnknownUserQuestion(queryRequest, botId);
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }

    @GetMapping("selectHotUser")
    public IBotResponse getHotUser(@RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate,
                                   @RequestParam("top") Integer top,
                                   @RequestParam(value = "botId", required = false) Long botId) {
        Map<String, Object> data = statisticService.getHotUser(startDate, endDate, top, botId);
        return new IBotResponse().data(data);
    }

    @GetMapping("selectBotHandleCount")
    public IBotResponse getBotHandleCount(@RequestParam("startDate") String startDate,
                                          @RequestParam("endDate") String endDate,
                                          @RequestParam(value = "botId", required = false) Long botId) {
        Map<String, Object> data = statisticService.getBotHandleCount(startDate, endDate, botId);
        return new IBotResponse().data(data);
    }

    @GetMapping("statisticThumbUpDown")
    public IBotResponse getStatisticThumbUpDown(@RequestParam("pageNo") Integer pageNo,
                                                @RequestParam("pageSize") Integer pageSize,
                                                @RequestParam("type") Integer type,
                                                @RequestParam(value = "botId", required = false) Long botId) {
        IPage<ThumbUpDownDto> page = null;
        if (type == 0) { //获取点赞用户问题记录
            page = statisticService.statisticThumbUp(pageNo, pageSize, botId);
        }else if (type == 1) { //获取点踩用户问题记录
            page = statisticService.statisticThumbDown(pageNo, pageSize, botId);
        }else {
            return new IBotResponse().message("请提供正确获取类型");
        }
        Map<String, Object> data = getDataTable(page);
        return new IBotResponse().data(data);
    }
}
