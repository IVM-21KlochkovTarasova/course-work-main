package ru.rsatu.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "test_try")
@NoArgsConstructor
@Getter
@Setter
public class TestTry extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "test_try_gen")
    private Long testTryId;
    private Date startDate;
    private Date endDate;
    public Boolean isTest;
    private Boolean isFinal;
    private Boolean isComplete = false;
    private Boolean isSuccessful = false;

    @ManyToOne
    @JoinColumn(name="studyProgramId")
    @JsonIgnore
    private StudyProgram studyProgram;

    private Long contractId;

//    @OneToMany(mappedBy="testTry",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<TryAnswers> tryAnswers = new HashSet<>();

}