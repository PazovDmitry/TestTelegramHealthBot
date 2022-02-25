package ru.task.TelegramBot.HealthBot.bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class Starter implements CommandLineRunner {

    private final HealthBot healthBot;

    public Starter(HealthBot healthBot) {
        this.healthBot = healthBot;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this.healthBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
