package ru.rsatu.dto;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class StatisticsResponse {

    List<StatisticDTO> statistics;
    int countPage;

}
