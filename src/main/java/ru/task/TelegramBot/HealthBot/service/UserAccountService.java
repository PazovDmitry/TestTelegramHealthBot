package ru.task.TelegramBot.HealthBot.service;

import ru.task.TelegramBot.HealthBot.entities.UserAccount;

import java.util.List;

public interface UserAccountService {

    UserAccount getByChatId(Long chatId);

    UserAccount addUserAccount(UserAccount account);

    UserAccount getUserAccountByChatId(Long chatid);

    UserAccount updateUserAccount(UserAccount userAccount);

    List<UserAccount> getAllUserAccounts();

    Long deleteByChatId(Long chatId);
}
