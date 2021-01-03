package com.taiping.ibot.server.knowledge.entity.qa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@TableName("qa_category")
public class QaCategory {
    /**
     * 类别 ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类别名
     */
    @TableField("name")
    @NotBlank(message = "{required}")
    @Size(max = 45, message = "{noMoreThan}")
    private String name;

    /**
     * 父类 ID
     */
    @TableField("pid")
    @NotNull(message = "{required}")
    private Long pid;

    /**
     * 知识库ID
     */
    @TableField("k_id")
    @NotNull(message = "{required}")
    private Long kid;

    /**
     * 知识库名称
     */
    @TableField(exist = false)
    private String knowledgeName;
}
