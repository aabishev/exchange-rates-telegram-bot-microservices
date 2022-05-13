package kz.example.microservice.exchangeratestelegrambotapp.model.handler;

import kz.example.microservice.exchangeratestelegrambotapp.model.BotState;
import kz.example.microservice.exchangeratestelegrambotapp.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class MessageHandler {
    private final ExchangeRateService exchangeRateService;

    private static final String textMessage = "Приветствую, я показываю информацию о курсах валют (USD, EUR, RUB) к тенге за 10 дней. Пожалуйста, выберите валюту!";

    public BotApiMethod<?> handle(Message message, BotState botState) {
        switch (botState.name()) {
            case ("START"):
                return exchangeRateService.getMainMenuMessage(message.getChatId(), textMessage);
            case ("USD"):
                return exchangeRateService.getUsdKztRatesHistory(message.getChatId());
            case ("EUR"):
                return exchangeRateService.getEurKztRatesHistory(message.getChatId());
            case ("RUB"):
                return exchangeRateService.getRubKztRatesHistory(message.getChatId());
            default:
                throw new IllegalStateException("Unexpected value: " + botState);
        }
    }
}
