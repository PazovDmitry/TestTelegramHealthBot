package ru.task.TelegramBot.HealthBot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.task.TelegramBot.HealthBot.entities.UserResponse;
import ru.task.TelegramBot.HealthBot.repository.UserResponseRepository;
import ru.task.TelegramBot.HealthBot.service.UserResponseService;

@Service
@RequiredArgsConstructor
public class UserResponseServiceImpl implements UserResponseService {
    private final UserResponseRepository repository;

    public UserResponse save(UserResponse userResponse) {
        return repository.save(userResponse);
    }
}
