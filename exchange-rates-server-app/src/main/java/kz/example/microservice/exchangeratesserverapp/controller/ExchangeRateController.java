package kz.example.microservice.exchangeratesserverapp.controller;

import kz.example.microservice.exchangeratesserverapp.dto.ExchangeRateDto;
import kz.example.microservice.exchangeratesserverapp.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/get-exchange-rates/{currency}")
    public ResponseEntity<?> getExchangeHistoryByCurrency(@PathVariable String currency) {
        List<ExchangeRateDto> exchangeRates = exchangeRateService.getExchangeRateDto(currency);
        if (exchangeRates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
    }
}
