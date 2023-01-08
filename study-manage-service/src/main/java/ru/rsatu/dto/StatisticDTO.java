package ru.rsatu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticDTO {

    private Long contractId;
    private String userId;
    private Long programId;
    private String programName;
    private Long testAmount;
    private Long testSuccAmount;
    private Long finalAmount;
    private Long finalFailAmount;

}
