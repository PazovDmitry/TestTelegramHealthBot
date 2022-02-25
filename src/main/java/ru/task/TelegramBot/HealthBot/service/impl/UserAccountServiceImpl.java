package ru.task.TelegramBot.HealthBot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.task.TelegramBot.HealthBot.entities.UserAccount;
import ru.task.TelegramBot.HealthBot.repository.UserAccountRepository;
import ru.task.TelegramBot.HealthBot.service.UserAccountService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;

    public UserAccount getByChatId(Long chatId) {
        if (chatId != null) {
            return this.userAccountRepository.findByChatId(chatId);
        } else {
            throw new IllegalArgumentException("chatId не может быть null");
        }
    }

    public UserAccount addUserAccount(UserAccount account) {
        return this.userAccountRepository.save(account);
    }

    public UserAccount getUserAccountByChatId(Long chatid) {
        UserAccount result = this.getByChatId(chatid);
        return result;
    }

    public UserAccount updateUserAccount(UserAccount userAccount) {
        return this.userAccountRepository.save(userAccount);
    }

    public List<UserAccount> getAllUserAccounts() {
        return this.userAccountRepository.findAll();
    }

    @Transactional
    public Long deleteByChatId(Long chatId){
        return this.userAccountRepository.deleteByChatId(chatId);
    }

}
