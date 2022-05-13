package kz.example.microservice.exchangeratesserverapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "currconv", url = "https://free.currconv.com/api/v7")
public interface CurrConvClient {
    @GetMapping("/convert")
    String getExchangeRatesHistory(@RequestParam("apiKey") String apiKey,
                                   @RequestParam("q") String q,
                                   @RequestParam("compact") String compact,
                                   @RequestParam("date") String date,
                                   @RequestParam("endDate") String endDate);
}
