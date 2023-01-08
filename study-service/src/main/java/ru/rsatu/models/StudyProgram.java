package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "study_program")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyProgram extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "study_program_gen")
    private Long studyProgramId;
    public String name;
    public String description;
    public Integer minimalDuration;
    public Integer completeTime;
    public Integer questionNums;
    public Integer triesCount;
    public Integer price;
    public Boolean isDeprecated;

    @ManyToOne
    @JoinColumn(name="courseId")
    @JsonIgnore
    public Course course;

    @OneToMany(mappedBy="studyProgram",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<StudyProgramLiterature> studyProgramLiteratures = new HashSet<>();

    @OneToMany(mappedBy="studyProgram",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy="studyProgram",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<TestTry> testTries = new HashSet<>();
}
