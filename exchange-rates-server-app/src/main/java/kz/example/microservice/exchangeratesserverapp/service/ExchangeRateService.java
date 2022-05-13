package kz.example.microservice.exchangeratesserverapp.service;

import kz.example.microservice.exchangeratesserverapp.dto.ExchangeRateDto;
import kz.example.microservice.exchangeratesserverapp.entity.ExchangeRate;
import kz.example.microservice.exchangeratesserverapp.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public List<ExchangeRateDto> getExchangeRateDto(String currency) {
        List<ExchangeRate> exchangeRates = exchangeRateRepository
                .getExchangeRateHistoriesByCurrencyOrderByExchangeDate(currency);
        List<ExchangeRateDto> exchangeRatesDtos = new ArrayList<>();
        for (ExchangeRate exchangeRate: exchangeRates) {
            ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
            exchangeRateDto.setExchangeDate(exchangeRate.getExchangeDate());
            exchangeRateDto.setRateValue(exchangeRate.getRateValue());
            exchangeRatesDtos.add(exchangeRateDto);
        }

        return exchangeRatesDtos;
    }
}
