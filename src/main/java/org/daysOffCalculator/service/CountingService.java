package org.daysOffCalculator.service;

import org.daysOffCalculator.dto.UserDayOffInfoDto;
import org.daysOffCalculator.dto.UserDaysOffPaymentDto;

public interface CountingService {
    UserDaysOffPaymentDto countDaysOffPayment(UserDayOffInfoDto userDayOffInfoDto);
}
