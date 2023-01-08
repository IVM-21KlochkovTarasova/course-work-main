package ru.rsatu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsatu.models.Answer;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrTestRespones {

    private Long testId;
    private Long tryAnswerId;
    private Long questionId;
    private String questionText;
    private List<Answer> answer;
    private Long amount;
    private Long curr;
}
