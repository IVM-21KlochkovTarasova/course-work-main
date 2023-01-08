package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "answer_gen")
    private Long answerId;
    private String answerText;
    private Boolean isRight;

    @ManyToOne
    @JoinColumn(name="questionId")
    @JsonIgnore
    public Question question;
//
//    @OneToMany(mappedBy="answer",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    public Set<TryAnswers> tryAnswers = new HashSet<>();
}
