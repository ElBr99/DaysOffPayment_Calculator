package org.daysOffCalculator.controller;

import lombok.RequiredArgsConstructor;
import org.daysOffCalculator.dto.UserDayOffInfoDto;
import org.daysOffCalculator.dto.UserDaysOffPaymentDto;
import org.daysOffCalculator.service.impl.CountingServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/days-off")
@RequiredArgsConstructor
public class DayOffPaymentController {

    private final CountingServiceImpl countingService;

    @GetMapping("/calculate")
    public UserDaysOffPaymentDto calculateDaysOffPayment(@ModelAttribute
                                                         @Validated UserDayOffInfoDto userDayOffInfoDto) {
        return countingService.countDaysOffPayment(userDayOffInfoDto);
    }


}
