package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "study_program")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyProgram extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "study_program_gen")
    private Long studyProgramId;
    private String name;
    private String description;
    private Integer minimalDuration;
    private Integer completeTime;
    private Integer questionNums;
    private Integer triesCount;
    private Integer price;
    private Boolean isDeprecated;

    @ManyToOne
    @JoinColumn(name="courseId")
    @JsonIgnore
    public Course course;

    @OneToMany(mappedBy="studyProgram",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<StudyProgramLiterature> studyProgramLiteratures = new HashSet<>();

//    @OneToMany(mappedBy="studyProgram",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    public Set<Contract> contracts = new HashSet<>();

    @OneToMany(mappedBy="studyProgram",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy="studyProgram",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<TestTry> testTries = new HashSet<>();
}
