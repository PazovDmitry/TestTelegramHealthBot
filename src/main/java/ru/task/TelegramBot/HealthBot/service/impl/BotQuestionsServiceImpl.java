package ru.task.TelegramBot.HealthBot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.task.TelegramBot.HealthBot.entities.BotQuestions;
import ru.task.TelegramBot.HealthBot.repository.BotQuestionsRepository;
import ru.task.TelegramBot.HealthBot.service.BotQuestionsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotQuestionsServiceImpl implements BotQuestionsService {

    private final BotQuestionsRepository repository;

    @Override
    public List<BotQuestions> getAll() {
        return repository.findAll();
    }
}