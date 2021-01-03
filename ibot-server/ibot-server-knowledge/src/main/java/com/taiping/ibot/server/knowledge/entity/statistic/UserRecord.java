package com.taiping.ibot.server.knowledge.entity.statistic;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Fei
 */
@Data
@ToString
@TableName("user_record")
public class UserRecord {
    @TableId
    private String recordId;

    @TableField("sender_id")
    private String senderId;

    @TableField("bot_id")
    private Long botId;

    @TableField("user_question")
    private String userQuestion;

    @TableField("bot_answer")
    private String botAnswer;

    @TableField("bot_type")
    private Character botType;

    @TableField("thumb_up")
    private Integer thumbUp;

    @TableField("thumb_down")
    private Integer thumbDown;

    @TableField("channel")
    private String channel;

    @TableField("std_question_id")
    private Long stdQuestionId;

    @TableField("department")
    private String department;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("send_time")
    private LocalDateTime sendTime;

    @TableField(exist = false)
    private String sendTimeFrom;

    @TableField(exist = false)
    private String sendTimeTo;
}
