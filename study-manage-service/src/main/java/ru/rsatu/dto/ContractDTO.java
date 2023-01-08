package ru.rsatu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import ru.rsatu.models.StudyProgram;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Value
public class ContractDTO {

    Long contractId;
    String userID;
    String username;
    Date enrollmentDate;
    Boolean isComplete;
    Long studyProgramId;
    String studyProgramName;
}
