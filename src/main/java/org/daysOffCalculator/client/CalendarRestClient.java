package org.daysOffCalculator.client;

import lombok.RequiredArgsConstructor;
import org.daysOffCalculator.config.ApplicationCalendarProperties;
import org.daysOffCalculator.dto.StatisticDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class CalendarRestClient {

    private final RestClient restClient;

    private final ApplicationCalendarProperties applicationCalendarProperties;


    public StatisticDto getDaysOffFromPeriod(LocalDate daysOffStartedAt, LocalDate daysOffFinishedAt) {
        String startedAt = daysOffStartedAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String finishedAt = daysOffFinishedAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        String period = startedAt.concat("-").concat(finishedAt);

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("token", applicationCalendarProperties.getToken());
        uriParams.put("country", applicationCalendarProperties.getCountry());
        uriParams.put("period", period);
        uriParams.put("format", "json");


        return restClient
                .get()
                .uri("/get-period/{token}/{country}/{period}/{format}", uriParams)
                .retrieve()
                .body(StatisticDto.class);

    }
}
