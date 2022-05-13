package kz.example.microservice.exchangeratestelegrambotapp.config;

import kz.example.microservice.exchangeratestelegrambotapp.botconfig.TelegramBotConfig;
import kz.example.microservice.exchangeratestelegrambotapp.model.TelegramBot;
import kz.example.microservice.exchangeratestelegrambotapp.model.TelegramFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final TelegramBotConfig telegramBotConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramBotConfig.getWebHookPath()).build();
    }

    @Bean
    public TelegramBot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        TelegramBot telegramBot = new TelegramBot(telegramFacade, setWebhook);
        telegramBot.setBotToken(telegramBotConfig.getBotToken());
        telegramBot.setBotUsername(telegramBotConfig.getUserName());
        telegramBot.setBotPath(telegramBotConfig.getWebHookPath());

        return telegramBot;
    }
}
