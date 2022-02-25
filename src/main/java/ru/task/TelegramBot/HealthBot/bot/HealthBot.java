package ru.task.TelegramBot.HealthBot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.task.TelegramBot.HealthBot.entities.BotQuestions;
import ru.task.TelegramBot.HealthBot.entities.UserAccount;
import ru.task.TelegramBot.HealthBot.entities.UserResponse;
import ru.task.TelegramBot.HealthBot.enums.Step;
import ru.task.TelegramBot.HealthBot.repository.BotMessagesRepository;
import ru.task.TelegramBot.HealthBot.service.BotQuestionsService;
import ru.task.TelegramBot.HealthBot.service.impl.UserAccountServiceImpl;
import ru.task.TelegramBot.HealthBot.service.UserResponseService;
import ru.task.TelegramBot.HealthBot.utils.BotUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class HealthBot extends TelegramLongPollingBot {

    private final BotMessagesRepository botMessagesRepository;
    private final UserAccountServiceImpl userAccountService;
    private final BotQuestionsService botQuestionsService;
    private final UserResponseService userResponseService;

    Map<Long, UserResponse> answers = new HashMap<>();
    Random random = new Random();

    @Override
    public String getBotUsername() {
        return "TestHealthBot_bot";
    }

    @Override
    public String getBotToken() {
        return "5175600350:AAFGQTIImkpYjxc-gAiHeHXzsDrt91xfiSE";
    }


    public void onUpdateReceived(Update update) {
        if (update != null && update.hasMessage()){
            this.processMessage(update);
        } else if (update.hasCallbackQuery()) {
            this.processCallBack(update);
        }
    }

    private void processMessage(Update update) {
        Message message = update.getMessage();
        if (message.getText() != null) {
            if (message.getText().equalsIgnoreCase("/start")) {
                this.processStartCommand(message);
            } else if (message.getText().equalsIgnoreCase("/register")) {
                this.processRegisterCommand(message);
            } else {
                UserAccount account = this.userAccountService.getByChatId(message.getChatId());
                if (account != null) {
                    if (account.getStep() == Step.QUESTION) {
                        UserResponse userResponse = answers.get(message.getChatId());
                        if (userResponse != null) {
                            userResponse.setResponse(message.getText());
                            userResponse.setAnswerDate(LocalDateTime.now());
                            this.userResponseService.save(userResponse);
                            answers.remove(message.getChatId());
                            BotUtils.sendMessage(this, "Спасибо за ваш ответ", message.getChatId());
                        }
                    }
                    this.continueProcessingUser(account, update);
                } else {
                    UserAccount user = this.userAccountService.getUserAccountByChatId(message.getChatId());
                    if (user == null) {
                        BotUtils.sendMessage(this,
                                "Для заполнения анкеты введи /register",
                                message.getChatId(), ParseMode.MARKDOWNV2);
                    } else {
                        BotUtils.sendMessage(this, "Ваша анкета уже заполнена\\.", message.getChatId(),
                                ParseMode.MARKDOWNV2);
                    }
                }
            }
        }
    }

    @Scheduled(fixedRateString = "600000")
    public void askClosedQuestion() {
        saveNonAnsweredQuestion();

        List<BotQuestions> allQuestions = botQuestionsService.getAll();
        List<BotQuestions> closedQuestionList = allQuestions.stream()
                .filter(botQuestions -> !botQuestions.getOpenQuestion())
                .collect(Collectors.toList());
        List<UserAccount> accounts = userAccountService.getAllUserAccounts();
        accounts = accounts.stream().filter(UserAccount::getActive).collect(Collectors.toList());
        if (!accounts.isEmpty()) {
            for (UserAccount account : accounts) {
                UserResponse userResponse = new UserResponse();
                String question = closedQuestionList.get(random.nextInt(closedQuestionList.size())).getText();
                userResponse.setChatId(account.getChatId());
                userResponse.setQuestion(question);
                userResponse.setCreateDate(LocalDateTime.now());
                answers.put(account.getChatId(), userResponse);
                BotUtils.sendUserResponse(this, question, account.getChatId());
            }
        }
    }

    public void askOpenQuestion() {
        List<BotQuestions> allQuestions = botQuestionsService.getAll();
        List<BotQuestions> openQuestionList = allQuestions.stream()
                .filter(BotQuestions::getOpenQuestion)
                .collect(Collectors.toList());
        List<UserAccount> accounts = userAccountService.getAllUserAccounts();
        accounts = accounts.stream().filter(UserAccount::getActive).collect(Collectors.toList());
        if (!accounts.isEmpty()) {
            for (UserAccount account : accounts) {
                UserResponse userResponse = new UserResponse();
                String question = openQuestionList.get(random.nextInt(openQuestionList.size())).getText();
                userResponse.setChatId(account.getChatId());
                userResponse.setQuestion(question);
                userResponse.setCreateDate(LocalDateTime.now());
                answers.put(account.getChatId(), userResponse);
                BotUtils.sendOpenQuestionResponse(this, question, account.getChatId());
            }
        }
    }

    private void saveNonAnsweredQuestion() {
        if (!answers.isEmpty()) {
            for (UserResponse userResponse : answers.values()) {
                userResponse.setAnswerDate(LocalDateTime.now());
                userResponse.setResponse("Нет ответа");
                userResponseService.save(userResponse);
            }
        }
    }

    private void processStartCommand(Message message) {
        BotUtils.sendMessage(this, botMessagesRepository.findById(1).get().getText(), message.getChatId());
    }

    private void processRegisterCommand(Message message) {
        UserAccount userAccount = new UserAccount();
        userAccount.setChatId(message.getChatId());
        userAccount.setActive(false);
        userAccount.setStep(Step.SURNAME);
        userAccountService.addUserAccount(userAccount);
        BotUtils.sendMessage(this, "Приступаем к заполнению анкеты", message.getChatId());
        this.showMessageFromStep(userAccount);
    }

    private void showMessageFromStep(UserAccount account) {
        Long chatId = account.getChatId();
        switch (account.getStep()) {
            case SURNAME:
                BotUtils.sendMessage(this, "Шаг 1\nВведи свою фамилию\\.", chatId, ParseMode.MARKDOWNV2);
                break;
            case NAME:
                BotUtils.sendMessage(this, "Шаг 2\nВведи свое имя\\.", chatId, ParseMode.MARKDOWNV2);
                break;
            case PATRONYMIC:
                BotUtils.sendMessage(this, "Шаг 3\nВведи свое отчетсво\\.", chatId, ParseMode.MARKDOWNV2);
                break;
            case FINISH:
                BotUtils.sendYesNoQuestion(this, "Последний шаг\nТы согласен на сохранение своих данных?", chatId);
                break;
            default:
                break;
        }
    }

    private void continueProcessingUser(UserAccount account, Update update) {
        if (account != null && update != null) {
            if (account.getStep() != Step.START || account.getStep() != Step.FINISH) {
                String textValue = update.getMessage().getText();
                switch (account.getStep()) {
                    case SURNAME:
                        account.setSurname(textValue);
                        account.setStep(Step.NAME);
                        break;
                    case NAME:
                        account.setName(textValue);
                        account.setStep(Step.PATRONYMIC);
                        break;
                    case PATRONYMIC:
                        account.setPatronymic(textValue);
                        account.setStep(Step.FINISH);
                        break;
                    default:
                        break;
                }
                account = this.userAccountService.updateUserAccount(account);
            }

            this.showMessageFromStep(account);
        }
    }

    private void processCallBack(Update update) {
        if (update.getCallbackQuery().getData() != null) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            UserAccount account = userAccountService.getByChatId(chatId);
            String response = update.getCallbackQuery().getData();

                String answer = "";
                UserResponse userResponse = new UserResponse();

                switch (response.toLowerCase()) {
                    case "yes":
                        account.setActive(true);
                        account.setStep(Step.QUESTION);
                        this.userAccountService.updateUserAccount(account);
                        answer = "Заполнение анкеты завершено\\. Функция изменения данных анкеты пока в разработке))";
                        break;
                    case "no":
                        this.userAccountService.deleteByChatId(chatId);
                        answer = "Заполнение анкеты прервано\\. Введи /register, чтобы начать заполнение анкеты";
                        break;
                    case "good":
                        userResponse = answers.get(chatId);
                        if (userResponse != null) {
                            userResponse.setResponse("Хорошо");
                            userResponse.setAnswerDate(LocalDateTime.now());
                            userResponseService.save(userResponse);
                            answers.remove(chatId);
                        }
                        this.askOpenQuestion();
                        break;
                    case "bad":
                        userResponse = answers.get(chatId);
                        if (userResponse != null) {
                            userResponse.setResponse("Плохо");
                            userResponse.setAnswerDate(LocalDateTime.now());
                            userResponseService.save(userResponse);
                            answers.remove(chatId);
                        }
                        this.askOpenQuestion();
                        break;
                    default:
                        answer = "Ошибка!";
                        break;
                }
                if (!answer.isEmpty()) {
                    BotUtils.sendCallBackMessage(this, update.getCallbackQuery().getMessage(), answer);
                }


        }
    }
}
