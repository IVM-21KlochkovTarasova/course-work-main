package ru.rsatu.dto;

import lombok.*;
import ru.rsatu.models.Contract;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ContractDTO {

    private Long contractId;
    private String userId;
    private String username;
    private Long studyProgramId;
    private LocalDateTime enrollmentDate;
    private Boolean isComplete;

    public ContractDTO(Contract contract){
        this.contractId = contract.getContractId();
        this.userId = contract.getUserId();
        this.studyProgramId = contract.getStudyProgramId();
        this.enrollmentDate = contract.getEnrollmentDate();
        this.isComplete = contract.getIsComplete();
        this.username = contract.getUsername();
    }
}
