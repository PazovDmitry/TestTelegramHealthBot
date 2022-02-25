package ru.task.TelegramBot.HealthBot.utils;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class BotUtils {
    public static void sendMessage(DefaultAbsSender sender, String text, Long chatId) {
        sendMessage(sender, text, chatId, null);
    }

    public static void sendMessage(DefaultAbsSender sender, String text, Long chatId, String parseMode) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(parseMode);
        sendMessage.setText(text);
        sendMessage.setChatId(chatId.toString());
        sendMessage(sender, sendMessage);
    }

    public static void sendMessage(DefaultAbsSender sender, SendMessage message) {
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /*public static void sendMessage(DefaultAbsSender sender, String message, Long[] chatIds) {
        if (chatIds != null && chatIds.length > 0 && message != null) {
            for (Long chatId : chatIds) {
                sendMessage(sender, message, chatId);
            }
        }
    }*/

    public static void sendCallBackMessage(DefaultAbsSender sender, Message callbackMessage, String answer) {
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(callbackMessage.getChatId().toString());
        new_message.setMessageId(callbackMessage.getMessageId());
        new_message.setText(answer);
        new_message.setParseMode(ParseMode.MARKDOWNV2);
        try {
            sender.execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /*public static void sendResponse(DefaultAbsSender sender, Message message, String answer) {
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(message.getChatId().toString());
        new_message.setMessageId(message.getMessageId());
        new_message.setText(answer);
        new_message.setParseMode(ParseMode.MARKDOWNV2);
        try {
            sender.execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }*/

    public static void sendYesNoQuestion(DefaultAbsSender sender, String message, Long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> inlineButtons = new ArrayList<>();

        InlineKeyboardButton btnYes = new InlineKeyboardButton();
        btnYes.setText("Да");
        btnYes.setCallbackData("yes");

        InlineKeyboardButton btnNo = new InlineKeyboardButton();
        btnNo.setText("Нет");
        btnNo.setCallbackData("no");

        inlineButtons.addAll(List.of(btnYes,btnNo));

        rowsInline.add(inlineButtons);
        markupInline.setKeyboard(rowsInline);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(markupInline);

        sendMessage(sender, sendMessage);
    }

    public static void sendUserResponse(DefaultAbsSender sender, String message, Long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> inlineButtons = new ArrayList<>();

        InlineKeyboardButton btnGood = new InlineKeyboardButton();
        btnGood.setText("Хорошо");
        btnGood.setCallbackData("good");

        InlineKeyboardButton btnBad = new InlineKeyboardButton();
        btnBad.setText("Плохо");
        btnBad.setCallbackData("bad");

        inlineButtons.addAll(List.of(btnGood, btnBad));

        rowsInline.add(inlineButtons);
        markupInline.setKeyboard(rowsInline);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(markupInline);

        sendMessage(sender, sendMessage);
    }

    public static void sendOpenQuestionResponse(DefaultAbsSender sender, String message, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage(sender, sendMessage);
    }
}
