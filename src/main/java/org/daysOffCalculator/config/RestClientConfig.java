package org.daysOffCalculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient createCalendarRestClient(ApplicationCalendarProperties calendarProperties) {
        return RestClient
                .builder()
                .baseUrl(calendarProperties.getDomain())
                .build();
    }
}
