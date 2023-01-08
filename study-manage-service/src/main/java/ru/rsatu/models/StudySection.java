package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity()
@Table(name = "study_section")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudySection extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "study_section_gen")
    private Long studySectionId;
    private String name;
    private String description;
    private Boolean isDeprecated;

    @OneToMany(mappedBy="studySection",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Course> courses;

}
