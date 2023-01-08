package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "course_gen")
    private Long courseId;
    private String name;
    private String description;
    private Boolean isDeprecated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studySectionId")
    @JsonIgnore
    private StudySection studySection;

    @OneToMany(mappedBy="course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StudyProgram> studyPrograms = new HashSet<>();
}
