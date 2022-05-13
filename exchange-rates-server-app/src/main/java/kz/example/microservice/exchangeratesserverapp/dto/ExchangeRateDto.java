package kz.example.microservice.exchangeratesserverapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ExchangeRateDto {
    @JsonFormat(pattern="yyyy-MM-dd")
    Date exchangeDate;
    Double rateValue;
}
