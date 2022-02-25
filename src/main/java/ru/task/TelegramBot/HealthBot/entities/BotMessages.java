package ru.task.TelegramBot.HealthBot.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "bot_messages")
public class BotMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text")
    private String text;

}
