package ru.task.TelegramBot.HealthBot.service;

import ru.task.TelegramBot.HealthBot.entities.BotQuestions;

import java.util.List;

public interface BotQuestionsService {
    List<BotQuestions> getAll();
}
