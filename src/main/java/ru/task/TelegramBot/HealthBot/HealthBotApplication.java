package ru.task.TelegramBot.HealthBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HealthBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthBotApplication.class, args);
	}

}
