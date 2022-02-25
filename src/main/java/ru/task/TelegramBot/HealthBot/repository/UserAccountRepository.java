package ru.task.TelegramBot.HealthBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.TelegramBot.HealthBot.entities.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByChatId(Long chatId);
    Long deleteByChatId(Long chatId);
}
