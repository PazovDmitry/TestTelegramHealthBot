package ru.task.TelegramBot.HealthBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.TelegramBot.HealthBot.entities.BotMessages;

@Repository
public interface BotMessagesRepository extends JpaRepository<BotMessages, Integer> {

}
