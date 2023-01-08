package ru.rsatu.dto;

import lombok.Value;

@Value
public class AnswerDTO {

    Long answerId;
    String answerText;
    Boolean isRight;
    
}
