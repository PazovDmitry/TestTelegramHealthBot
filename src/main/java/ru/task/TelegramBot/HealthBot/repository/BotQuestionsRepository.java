package ru.task.TelegramBot.HealthBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.TelegramBot.HealthBot.entities.BotQuestions;

@Repository
public interface BotQuestionsRepository extends JpaRepository<BotQuestions, Integer> {

}
