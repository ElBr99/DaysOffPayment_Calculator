package org.daysOffCalculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor
public class UserDayOffInfoDto {

    @NotNull
    BigDecimal averageSalary;

    Integer daysOffAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dayOffStartedAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dayOffFinishedAt;

}
