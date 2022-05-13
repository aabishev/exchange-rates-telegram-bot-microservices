package kz.example.microservice.exchangeratestelegrambotapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "exchangeratesserverapp", url = "https://localhost:8082/exchangeratesserverapp/api/v1/get-exchange-rates")
public interface ExchangeRatesServerAppClient {
    @GetMapping("/{currency}")
    String getExchangeRatesHistory(@PathVariable String currency);
}
