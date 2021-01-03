package com.taiping.ibot.server.knowledge.entity.qa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.taiping.ibot.server.knowledge.converter.ExcelDateFieldConverter;
import com.taiping.ibot.server.knowledge.converter.ExcelEmptyCellConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ToString
@TableName("qa")
@Excel("知识表")
public class Qa implements Serializable {
    private static final long serialVersionUID = 2631590509760923211L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    @ExcelField(value = "ID")
    private Long id;

    @TableField(exist = false)
//    @ExcelField(value = "知识库")
    private String knowledgeName;

    @TableField(exist = false)
    @ExcelField(value = "分类", required = true, readConverter = ExcelEmptyCellConverter.class)
    private String categoryName;

    @TableField("question")
    @Size(min = 2, max = 2000, message = "{range}")
    @ExcelField(value = "标准问题", required = true, readConverter = ExcelEmptyCellConverter.class, comment = "标准问题字段必填")
    private String question;

    @TableField(exist = false)
    @ExcelField(value = "相似问题", readConverter = ExcelEmptyCellConverter.class, comment = "相似问题，多个以\"||\"分开")
    private String similarQuestion;

    @TableField("category_id")
    @NotNull(message = "{required}")
    private Long categoryId;

    @TableField("pid")
    @NotNull(message = "{required}")
    private Long pid;

    @TableField("k_id")
    private Long kid;

    @TableField("user_id")
    private Long userId;

    @TableField("answer")
    @ExcelField(value = "答案", required = true, readConverter = ExcelEmptyCellConverter.class, comment = "答案字段必填")
    private String answer;

    @TableField("status")
    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("start_date")
    @ExcelField(value = "开始日期", readConverter = ExcelDateFieldConverter.class, comment = "日期必须以字符串输入，格式为：yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("end_date")
    @ExcelField(value = "结束日期", readConverter = ExcelDateFieldConverter.class, comment = "日期必须以字符串输入，格式为：yyyy-MM-dd")
    private LocalDate endDate;

    @TableField("pic_url")
    @ExcelField(value = "图片url", readConverter = ExcelEmptyCellConverter.class)
    private String picUrl;

    @TableField("video_url")
    @ExcelField(value = "视频url", readConverter = ExcelEmptyCellConverter.class)
    private String videoUrl;

    @TableField("tag")
    @ExcelField(value = "标签", readConverter = ExcelEmptyCellConverter.class)
    private String tag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String similarQid;

    @TableField(exist = false)
    private String createTimeFrom;

    @TableField(exist = false)
    private String createTimeTo;
}
