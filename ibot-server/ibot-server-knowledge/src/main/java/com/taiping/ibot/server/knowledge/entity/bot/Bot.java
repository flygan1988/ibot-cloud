package com.taiping.ibot.server.knowledge.entity.bot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author Fei
 */
@Data
@ToString
@TableName("bot")
public class Bot {
    @TableId(value = "id", type = IdType.AUTO)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @TableField("bot_name")
    @NotBlank(message = "{require}")
    @Size(max = 45, message = "{noMoreThan}")
    private String botName;

    @TableField("description")
    @Size(max = 200, message = "{noMoreThan}")
    private String description;

    @TableField("user_id")
    private Long userId;

    @TableField("dept_id")
    private Long deptId;

    @TableField("domain")
    private String domain;

    @TableField("top_answer")
    private Integer topAnswer;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String knowledgeId;

    @TableField(exist = false)
    private String knowledgeName;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String deptName;
}
