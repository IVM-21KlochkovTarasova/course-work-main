package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity()
@Table(name = "try_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TryAnswers extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "try_answers_gen")
    private Long tryAnswersId;
    public Integer npp;

    @ManyToOne
    @JoinColumn(name="testTryId")
    @JsonIgnore
    public TestTry testTry;

    @ManyToOne
    @JoinColumn(name="questionId")
    @JsonIgnore
    public Question question;

    @ManyToOne
    @JoinColumn(name="answerId")
    @JsonIgnore
    public Answer answer;
}
