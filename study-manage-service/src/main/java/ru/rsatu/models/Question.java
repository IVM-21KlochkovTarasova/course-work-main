package ru.rsatu.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "question_gen")
    private Long questionId;
    private String questionText;
    private String imageURL;
    private String answerInformation;

    @ManyToOne
    @JoinColumn(name="studyProgramId")
    @JsonIgnore
    public StudyProgram studyProgram;

    @OneToMany(mappedBy="question",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Answer> answers = new HashSet<>();

//    @OneToMany(mappedBy="question",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    public Set<TryAnswers> tryAnswers = new HashSet<>();


}
