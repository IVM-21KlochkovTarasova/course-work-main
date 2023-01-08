package ru.rsatu.dto;

import lombok.Value;

@Value
public class CourseDTO {

    Long courseId;
    String name;
    String description;
    Boolean isDeprecated;
    Long studySectionId;

}
