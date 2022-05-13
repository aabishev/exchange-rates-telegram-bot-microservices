package kz.example.microservice.exchangeratesserverapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.example.microservice.exchangeratesserverapp.client.CurrConvClient;
import kz.example.microservice.exchangeratesserverapp.entity.ExchangeRate;
import kz.example.microservice.exchangeratesserverapp.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateUpdateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrConvClient currConvClient;
    private final ObjectMapper objectMapper;

    private static final String[] RATES = {"USD_KZT", "EUR_KZT", "RUB_KZT"};
    private static final String API_KEY = "68e034b4e8a629b9409d";
    private static final String COMPACT = "ultra";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Scheduled(cron = "0 0 6 * * *")
    public void updateExchangeRates() {
        exchangeRateRepository.deleteAll();
        for (String rate: RATES) {
            updateCurrencyRates(rate);
            log.info("UPDATED {} HISTORY FOR:{}", rate, LocalDate.now());
        }
    }

    private void updateCurrencyRates(String currency) {
        Map<String, Object> exchangeRatesHistory = objectMapper
                .convertValue(getExchangeRatesByCurrency(currency).get(currency), new TypeReference<>() {});
        for (Map.Entry<String, Object> map : exchangeRatesHistory.entrySet()) {
            ExchangeRate exchangeRate = new ExchangeRate();

            try {
                Date date = new SimpleDateFormat(DATE_PATTERN).parse(map.getKey());
                exchangeRate.setExchangeDate(date);
            } catch (ParseException exception) {
                log.error("An error occurred while parsing the object");
            }

            exchangeRate.setRateValue((Double) map.getValue());
            exchangeRate.setCurrency(currency);
            exchangeRateRepository.save(exchangeRate);
        }
    }

    private JsonNode getExchangeRatesByCurrency(String currency) {
        JsonNode response = null;

        try {
            response = objectMapper.readTree(currConvClient
                    .getExchangeRatesHistory
                            (API_KEY, currency, COMPACT,
                                    LocalDate.now().minusDays(8).format(DateTimeFormatter.ofPattern(DATE_PATTERN)),
                                    LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN))));
        } catch (JsonProcessingException exception) {
            log.error("An error occurred while serializing the object");
        }

        return response;
    }
}
