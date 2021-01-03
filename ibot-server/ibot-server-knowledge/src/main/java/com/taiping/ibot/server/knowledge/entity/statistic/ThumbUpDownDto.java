package com.taiping.ibot.server.knowledge.entity.statistic;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ThumbUpDownDto {
    private Long questionId;
    private String question;
    private String answer;
    private Long total;
    private LocalDateTime sendTime;
}
