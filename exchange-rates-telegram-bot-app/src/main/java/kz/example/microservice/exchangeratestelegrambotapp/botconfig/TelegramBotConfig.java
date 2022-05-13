package kz.example.microservice.exchangeratestelegrambotapp.botconfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TelegramBotConfig {
    @Value("@azamat_exchange_rates_bot")
    private String webHookPath;
    @Value("2010178585:AAE3feyJgDAeT0MTiNEUs581D7fOI9XXaiw")
    private String userName;
    @Value("https://exchangeratestelegrambot.herokuapp.com/")
    private String botToken;
}
