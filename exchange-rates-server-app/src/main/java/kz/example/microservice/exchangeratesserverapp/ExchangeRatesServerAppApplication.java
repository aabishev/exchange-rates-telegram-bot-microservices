package kz.example.microservice.exchangeratesserverapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
public class ExchangeRatesServerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRatesServerAppApplication.class, args);
    }

}
