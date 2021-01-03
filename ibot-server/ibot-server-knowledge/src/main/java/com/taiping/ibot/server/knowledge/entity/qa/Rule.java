package com.taiping.ibot.server.knowledge.entity.qa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ToString
@TableName("rule")
@Excel("关键词规则表")
public class Rule {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("rule_trigger")
    private String ruleTrigger;

    @TableField("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @TableField("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @TableField("start_time")
//    @JsonFormat(pattern = "HH:mm:ss")
    private String startTime;

    @TableField("end_time")
//    @JsonFormat(pattern = "HH:mm:ss")
    private String endTime;

    @TableField("match_type")
    @NotNull(message = "{required}")
    private Integer matchType; //0:等于关键字， 1:包含关键字

    @TableField("keywords")
    @NotBlank(message = "{required}")
    private String keywords;

    @TableField("response")
    @NotBlank(message = "{required}")
    private String response;

    @TableField("affect")
    private Boolean affect;

    @TableField("user_id")
    private Long userId;

    @TableField("dept_id")
    private Long deptId;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private String botId;

    @TableField(exist = false)
    private String botName;
}
