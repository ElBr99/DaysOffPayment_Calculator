package org.daysOffCalculator.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.daysOffCalculator.client.CalendarRestClient;
import org.daysOffCalculator.dto.StatisticDto;
import org.daysOffCalculator.dto.StatisticInfoDto;
import org.daysOffCalculator.dto.UserDayOffInfoDto;
import org.daysOffCalculator.dto.UserDaysOffPaymentDto;
import org.daysOffCalculator.exception.InvalidInfoException;
import org.daysOffCalculator.service.impl.CountingServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class CountingServiceImplTest {

    @Mock
    private CalendarRestClient calendarRestClient;

    @InjectMocks
    private CountingServiceImpl countingService;

    @Test
    void countDaysOffPayment_NullDaysOffPeriod_NotNullDaysOffAmount() {

        UserDayOffInfoDto userDayOffInfoDto = UserDayOffInfoDto.builder()
                .daysOffAmount(10)
                .averageSalary(BigDecimal.valueOf(2389))
                .dayOffStartedAt(null)
                .dayOffFinishedAt(null)
                .build();
        UserDaysOffPaymentDto expectedPayment = new UserDaysOffPaymentDto(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(2389)));

        UserDaysOffPaymentDto foundPayments = countingService.countDaysOffPayment(userDayOffInfoDto);
        verify(calendarRestClient, times(0)).getDaysOffFromPeriod(userDayOffInfoDto.getDayOffStartedAt(), userDayOffInfoDto.getDayOffFinishedAt());

        Assertions.assertEquals(expectedPayment.getDayOffPayment(), foundPayments.getDayOffPayment());

    }

    @Test
    void countDaysOffPayment_NullDaysOffAmount_NotNullDaysOffPeriod_NoHolidays_WithWeekends() {
        UserDayOffInfoDto userDayOffInfoDto = UserDayOffInfoDto.builder()
                .daysOffAmount(null)
                .averageSalary(BigDecimal.valueOf(2389))
                .dayOffStartedAt(LocalDate.of(2025, 4, 16))
                .dayOffFinishedAt(LocalDate.of(2025, 4, 30))
                .build();

        StatisticDto expectedStatistic = new StatisticDto(StatisticInfoDto.builder()
                .holidays(0)
                .calendarDays(15)
                .workDays(11)
                .weekends(4)
                .build());

        UserDaysOffPaymentDto expectedPayment = new UserDaysOffPaymentDto(BigDecimal.valueOf(11).multiply(BigDecimal.valueOf(2389)));

        when(calendarRestClient.getDaysOffFromPeriod(userDayOffInfoDto.getDayOffStartedAt(), userDayOffInfoDto.getDayOffFinishedAt())).thenReturn(expectedStatistic);

        UserDaysOffPaymentDto foundPayments = countingService.countDaysOffPayment(userDayOffInfoDto);
        verify(calendarRestClient, times(1)).getDaysOffFromPeriod(userDayOffInfoDto.getDayOffStartedAt(), userDayOffInfoDto.getDayOffFinishedAt());

        Assertions.assertEquals(expectedPayment.getDayOffPayment(), foundPayments.getDayOffPayment());


    }

    @Test
    void countDaysOffPayment_NullDaysOffAmount_NullDaysOffPeriod_ThrowsException() {
        UserDayOffInfoDto userDayOffInfoDto = UserDayOffInfoDto.builder()
                .daysOffAmount(null)
                .averageSalary(BigDecimal.valueOf(2389))
                .dayOffStartedAt(null)
                .dayOffFinishedAt(null)
                .build();

        Assertions.assertThrows(InvalidInfoException.class, () -> countingService.countDaysOffPayment(userDayOffInfoDto));

    }

    @Test
    void countDaysOffPayment_NullValidatedAverageSalary_NotNullOtherFields_ThrowsException() {
        UserDayOffInfoDto userDayOffInfoDto = UserDayOffInfoDto.builder()
                .daysOffAmount(11)
                .averageSalary(null)
                .dayOffStartedAt(LocalDate.of(2025, 4, 16))
                .dayOffFinishedAt(LocalDate.of(2025, 4, 30))
                .build();

        Assertions.assertThrows(InvalidInfoException.class, () -> countingService.countDaysOffPayment(userDayOffInfoDto));

    }


}
