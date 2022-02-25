package ru.task.TelegramBot.HealthBot.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_responses")
public class UserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "response")
    private String response;

    @Column(name = "question")
    private String question;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "answer_date")
    private LocalDateTime answerDate;

}
