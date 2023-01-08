package ru.rsatu.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class OrderDTO {
    private Long contractId;
    private String userId;
    private String username;
    private Long studyProgramId;
    private LocalDateTime enrollmentDate;
    private Boolean isComplete;
}
