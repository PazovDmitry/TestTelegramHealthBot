package ru.task.TelegramBot.HealthBot.entities;

import lombok.Data;
import ru.task.TelegramBot.HealthBot.enums.Step;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "step")
    @Enumerated(EnumType.STRING)
    private Step step;

}
