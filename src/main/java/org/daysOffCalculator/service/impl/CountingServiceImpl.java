package org.daysOffCalculator.service.impl;

import lombok.RequiredArgsConstructor;
import org.daysOffCalculator.client.CalendarRestClient;
import org.daysOffCalculator.dto.Algorithm;
import org.daysOffCalculator.dto.StatisticDto;
import org.daysOffCalculator.dto.UserDayOffInfoDto;
import org.daysOffCalculator.dto.UserDaysOffPaymentDto;
import org.daysOffCalculator.exception.InvalidInfoException;
import org.daysOffCalculator.service.CountingService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

import static org.daysOffCalculator.utils.AlgorithmResolver.getUserDaysOffPaymentDto;

@Service
@RequiredArgsConstructor
public class CountingServiceImpl implements CountingService {

    private final CalendarRestClient calendarRestClient;

    @Override
    public UserDaysOffPaymentDto countDaysOffPayment(@Validated UserDayOffInfoDto userDayOffInfoDto) {
        UserDaysOffPaymentDto userDaysOffPaymentDto = new UserDaysOffPaymentDto();
        Algorithm algorithm = getUserDaysOffPaymentDto(userDayOffInfoDto)
                .orElseThrow(() -> new InvalidInfoException("Введены неверные данные. Введите либо промежуток с (дата) и до (дата), либо введите общее кол-во дней отпуска"));

        switch (algorithm) {
            case DAY ->
                    userDaysOffPaymentDto.setDayOffPayment(getPayment(userDayOffInfoDto.getDaysOffAmount(), userDayOffInfoDto.getAverageSalary()));
            case PERIOD -> {
                StatisticDto statisticDto = calendarRestClient.getDaysOffFromPeriod(userDayOffInfoDto.getDayOffStartedAt(), userDayOffInfoDto.getDayOffFinishedAt());
                userDaysOffPaymentDto.setDayOffPayment(getPayment(statisticDto.getStatisticInfoDto().getWorkDays(), userDayOffInfoDto.getAverageSalary()));
            }
        }

        return userDaysOffPaymentDto;
    }


    public BigDecimal getPayment(int daysOff, BigDecimal averageSalary) {
        return averageSalary.multiply(BigDecimal.valueOf(daysOff));
    }
}
