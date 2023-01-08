package ru.rsatu.dto;

import lombok.Value;

@Value
public class StudyProgramDTO {

    Long studyProgramId;
    String name;
    String description;
    Integer minimalDuration;
    Integer completeTime;
    Integer questionNums;
    Integer triesCount;
    Integer price;
    Boolean isDeprecated;
    Long courseId;
    
}
