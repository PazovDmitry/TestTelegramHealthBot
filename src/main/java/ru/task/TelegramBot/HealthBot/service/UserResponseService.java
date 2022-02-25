package ru.task.TelegramBot.HealthBot.service;

import ru.task.TelegramBot.HealthBot.entities.UserResponse;

public interface UserResponseService {
    UserResponse save(UserResponse userResponse);
}
