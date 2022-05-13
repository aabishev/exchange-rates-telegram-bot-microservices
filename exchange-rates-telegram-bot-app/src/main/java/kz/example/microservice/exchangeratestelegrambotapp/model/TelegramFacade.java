package kz.example.microservice.exchangeratestelegrambotapp.model;

import kz.example.microservice.exchangeratestelegrambotapp.model.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TelegramFacade {
    private final MessageHandler messageHandler;

    public BotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            return handleInputMessage(message);
        }

        return null;
    }

    private BotApiMethod<?> handleInputMessage(Message message) {
        BotState botState;
        String inputMsg = message.getText();
        switch (inputMsg) {
            case "USD_KZT":
                botState = BotState.USD;
                break;
            case "EUR_KZT":
                botState = BotState.EUR;
                break;
            case "RUB_KZT":
                botState = BotState.RUB;
                break;
            default:
                botState = BotState.START;
        }

        return messageHandler.handle(message, botState);
    }
}
