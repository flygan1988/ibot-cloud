package com.taiping.ibot.server.knowledge.entity.qa;

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

@Data
@ToString
@TableName("knowledge_info")
public class KnowledgeInfo {
    /**
     * 知识库 ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 知识库名称
     */
    @TableField("knowledge_name")
    @NotBlank(message = "{require}")
    @Size(max = 45, message = "{noMoreThan}")
    private String knowledgeName;

    /**
     * 知识库描述
     */
    @TableField("description")
    @Size(max = 100, message = "{noMoreThan}")
    private String description;

    /**
     * 创建用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 创建用户所属部门
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 创建用户名称
     */
    @TableField(exist = false)
    private String username;

    /**
     * 创建用户所属部门
     */
    @TableField(exist = false)
    private String deptName;
}
