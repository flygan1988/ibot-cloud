package com.taiping.ibot.server.knowledge.entity.bot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

/**
 * @author Fei
 */
@Data
@ToString
@TableName("bot_rule")
public class BotRule {

    @TableId(value = "id", type = IdType.AUTO)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @TableField("bot_id")
    private Long botId;

    @TableField("rule_id")
    private Long ruleId;

}
