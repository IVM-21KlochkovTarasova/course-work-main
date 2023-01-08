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

/**
 * программа обучение
 */
@Entity()
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "course_gen")
    public Long courseId;
    public String name;
    public String description;
    public Boolean isDeprecated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studySectionId")
    @JsonIgnore
    private StudySection studySection;

    @OneToMany(mappedBy="course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<StudyProgram> studyPrograms = new HashSet<>();

}
