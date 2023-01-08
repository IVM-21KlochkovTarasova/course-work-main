package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;

@Entity()
@Table(name = "study_program_literature")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyProgramLiterature extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "study_program_literature_gen")
    private Long studyProgramLiteratureId;
    private String title;
    private String link;
    private String description;

    @ManyToOne
    @JoinColumn(name="studyProgramId")
    @JsonIgnore
    public StudyProgram studyProgram;

}
