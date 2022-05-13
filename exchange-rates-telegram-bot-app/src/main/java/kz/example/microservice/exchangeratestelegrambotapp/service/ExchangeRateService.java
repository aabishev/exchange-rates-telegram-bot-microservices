package kz.example.microservice.exchangeratestelegrambotapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.example.microservice.exchangeratestelegrambotapp.client.ExchangeRatesServerAppClient;
import kz.example.microservice.exchangeratestelegrambotapp.entity.ChatId;
import kz.example.microservice.exchangeratestelegrambotapp.repository.ChatIdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRatesServerAppClient exchangeRatesServerAppClient;
    private final ObjectMapper objectMapper;
    private final ChatIdRepository chatIdRepository;
    private final SendMessage sendMessage;

    private static final String[] RATES = {"USD_KZT", "EUR_KZT", "RUB_KZT"};

    public SendMessage getMainMenuMessage(Long chatId, String textMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        ChatId chatIdEntity = new ChatId();
        chatIdEntity.setUserChatId(chatId);
        chatIdRepository.save(chatIdEntity);

        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    private SendMessage createMessageWithKeyboard(long chatId, String textMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }

        return sendMessage;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton(RATES[0]));
        row2.add(new KeyboardButton(RATES[1]));
        row3.add(new KeyboardButton(RATES[2]));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public SendMessage getUsdKztRatesHistory(long chatId) {
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(getExchangeRatesHistory(RATES[0]));

        return sendMessage;
    }

    public SendMessage getEurKztRatesHistory(long chatId) {
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(getExchangeRatesHistory(RATES[1]));

        return sendMessage;
    }

    public SendMessage getRubKztRatesHistory(long chatId) {
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(getExchangeRatesHistory(RATES[2]));

        return sendMessage;
    }

    private String getExchangeRatesHistory(String currency) {
        StringBuilder exchangeHistoryDate = new StringBuilder();
        JsonNode response = getExchangeRatesByCurrency(currency);
        for (JsonNode node : response) {
            exchangeHistoryDate.append(node.get("exchangeDate").asText())
                    .append(" ---> ")
                    .append(node.get("rateValue").asDouble())
                    .append("\n");
        }

        return exchangeHistoryDate.toString();
    }

    private JsonNode getExchangeRatesByCurrency(String currency) {
        JsonNode response = null;

        try {
            response = objectMapper.readTree(exchangeRatesServerAppClient.getExchangeRatesHistory(currency));
        } catch (JsonProcessingException exception) {
            log.error("An error occurred while serializing the object");
        }

        return response;
    }
}
