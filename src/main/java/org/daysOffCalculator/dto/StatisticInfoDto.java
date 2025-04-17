package org.daysOffCalculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class StatisticInfoDto {
    @JsonProperty(value = "calendar_days")
    private int calendarDays;

    @JsonProperty(value = "calendar_days_without_holidays")
    private int calendarDaysWithoutHolidays;

    @JsonProperty(value = "work_days")
    private int workDays;

    @JsonProperty(value = "weekends")
    private int weekends;

    @JsonProperty(value = "holidays")
    private int holidays;

}
