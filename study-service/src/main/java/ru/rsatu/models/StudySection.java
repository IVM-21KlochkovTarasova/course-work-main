package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * раздел обучения
 */
@Entity()
@Table(name = "study_section")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudySection extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "study_section_gen")
    public Long studySectionId;
    public String name;
    public String description;
    public Boolean isDeprecated;

    @OneToMany(mappedBy="studySection",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Course> courses;

}
