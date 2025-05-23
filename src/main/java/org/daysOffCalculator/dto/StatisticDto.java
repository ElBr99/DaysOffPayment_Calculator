package org.daysOffCalculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class StatisticDto {

    @JsonProperty(value = "statistic")
    private StatisticInfoDto statisticInfoDto;

}
