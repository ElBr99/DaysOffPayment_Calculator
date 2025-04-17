package org.daysOffCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDaysOffPaymentDto {
    BigDecimal dayOffPayment;
}
