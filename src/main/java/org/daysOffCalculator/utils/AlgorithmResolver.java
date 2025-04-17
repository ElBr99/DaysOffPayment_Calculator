package org.daysOffCalculator.utils;

import lombok.Data;
import org.daysOffCalculator.dto.Algorithm;
import org.daysOffCalculator.dto.UserDayOffInfoDto;

import java.util.Optional;

@Data
public class AlgorithmResolver {


    public static Optional<Algorithm> getUserDaysOffPaymentDto(UserDayOffInfoDto userDayOffInfoDto) {
        if (userDayOffInfoDto.getDayOffFinishedAt() == null && userDayOffInfoDto.getDayOffStartedAt() == null && userDayOffInfoDto.getDaysOffAmount() != null) {
            return Optional.of(Algorithm.DAY);
        }

        if (userDayOffInfoDto.getDayOffFinishedAt() != null && userDayOffInfoDto.getDayOffStartedAt() != null && userDayOffInfoDto.getDaysOffAmount() == null) {
            return Optional.of(Algorithm.PERIOD);
        }

        return Optional.empty();

    }

}
