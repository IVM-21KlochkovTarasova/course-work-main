package ru.rsatu.dto;

import lombok.Builder;
import lombok.Data;
import ru.rsatu.models.StudyProgram;

@Data
@Builder
public class LkProgramDTO {

    OrderDTO contract;
    StudyProgram studyProgram;

}
