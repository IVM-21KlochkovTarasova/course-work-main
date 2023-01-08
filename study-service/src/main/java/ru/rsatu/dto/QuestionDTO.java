package ru.rsatu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsatu.models.Answer;
import ru.rsatu.models.Question;
import ru.rsatu.models.StudyProgram;
import ru.rsatu.models.TryAnswers;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    public Long questionId;
    public String questionText;
    public String imageURL;
    public String answerInformation;

    public QuestionDTO(Question question){
        this.questionId = question.questionId;
        this.questionText = question.questionText;
        this.imageURL = question.imageURL;
        this.answerInformation = question.answerInformation;
    }

//    @ManyToOne
//    @JoinColumn(name="studyProgramId")
//    @JsonIgnore
//    public StudyProgram studyProgram;
//
//    @OneToMany(mappedBy="question",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    public Set<Answer> answers = new HashSet<>();
//
//    @OneToMany(mappedBy="question",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    public Set<TryAnswers> tryAnswers = new HashSet<>();

}
