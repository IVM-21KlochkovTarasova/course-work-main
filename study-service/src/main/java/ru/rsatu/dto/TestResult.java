package ru.rsatu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResult {
    private Long questionId;
    private String questionText;
    private Long givenAnswerId;
    private String givenAnswerText;
    private Long rightAnswerId;
    private String rightAnswerText;
    private String rightAnswerInfo;
    private Integer npp;
}
