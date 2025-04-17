package org.daysOffCalculator.integration;

import org.junit.jupiter.api.Test;
import org.daysOffCalculator.AbstractIT;
import org.daysOffCalculator.dto.UserDayOffInfoDto;
import org.daysOffCalculator.dto.UserDaysOffPaymentDto;
import org.daysOffCalculator.service.impl.CountingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class DayOffPaymentControllerTest extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountingServiceImpl countingService;


    @Test
    void calculateDaysOffPayment() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        UserDayOffInfoDto userDayOffInfoDto = UserDayOffInfoDto.builder()
                .daysOffAmount(null)
                .averageSalary(BigDecimal.valueOf(2189))
                .dayOffStartedAt(LocalDate.of(2025, 4, 16))
                .dayOffFinishedAt(LocalDate.of(2025, 4, 30))
                .build();


        UserDaysOffPaymentDto userDaysOffPaymentDto = UserDaysOffPaymentDto.builder()
                .dayOffPayment(userDayOffInfoDto.getAverageSalary().multiply(BigDecimal.valueOf(11)))
                .build();

        String jsonUserDayOffPayment = new ObjectMapper().writeValueAsString(userDaysOffPaymentDto);

        stubFor(get(urlPathMatching("/get-period/6914a408120146bcb82ab95c003bc6ad/ru/10.10.2025-11.10.2025/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(loadJson("json/wireMockAnswer.json"))));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/days-off/calculate")
                        .queryParam("averageSalary", "2189")
                        .queryParam("dayOffStartedAt", "2025-10-10")
                        .queryParam("dayOffFinishedAt", "2025-10-11")
                )
                .andExpect(MockMvcResultMatchers.content().json(jsonUserDayOffPayment));

    }
}
