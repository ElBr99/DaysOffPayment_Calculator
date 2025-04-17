package org.daysOffCalculator.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.calendar")
@Value
public class ApplicationCalendarProperties {
    String domain;
    String token;
    String country;
    String region;
}
