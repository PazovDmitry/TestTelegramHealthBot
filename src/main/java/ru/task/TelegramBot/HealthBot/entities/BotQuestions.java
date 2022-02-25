package ru.task.TelegramBot.HealthBot.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "bot_questions")
public class BotQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "is_open_question")
    private Boolean openQuestion;

}
