package ru.rsatu.dto;

import lombok.Value;

@Value
public class StudySectionDTO {

    Long studySectionId;
    String name;
    String description;
    Boolean isDeprecated;

}
