package ru.rsatu.dto;

import lombok.Value;

import java.util.List;

@Value
public class QuestionDTO {

    Long questionId;
    String questionText;
    String imageURL;
    String answerInformation;
    Long programId;
    List<AnswerDTO> answerList;

}
