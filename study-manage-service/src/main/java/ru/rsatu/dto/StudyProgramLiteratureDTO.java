package ru.rsatu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import ru.rsatu.models.StudyProgram;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Value
public class StudyProgramLiteratureDTO {

    Long studyProgramLiteratureId;
    String title;
    String link;
    String description;
    Long studyProgramId;

}
