package com.example.telegrambot;

import com.example.telegrambot.dto.VacancyDto;
import com.example.telegrambot.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class VacanciesBot extends TelegramLongPollingBot {

    @Autowired
    private VacancyService vacancyService;

    private final Map<Long, String> lastShownVacancyLevel = new HashMap<>();

//    private final List<String> correctWordsBased = new ArrayList<>(List.of("To take care of someone or something",
//            "To look and see what there is near you. To walk around a room, building, store or city", "To remember or think of something nostalgically",
//            "To think you are better than someone. To view someone or something as inferior or not good enough", "To search",
//            "To feel excited about something that is going to happen in the future", "To investigate or look for more information",
//            "To watch something as a spectator", "To be attentive or careful. Often used as an order", "To examine or review something",
//            "To search for information in a dictionary or other database", "To respect or admire someone", "To examine something", "To visit somebody"));
//            /*"ing: I enjoyed living in France", "ing: I fancy seeing a film tonight",
//            "ing: We discussed going on holiday together", "ing: I dislike waiting for buses", "ing: We've finished preparing for the meeting",
//            "ing: I don't mind coming early", "ing: He suggested staying at the Grand Hotel", "ing: They recommended meeting earlier",
//            "ing: He kept working, although he felt ill", "ing: She avoided talking to her boss", "ing: She misses living near the beach",
//            "ing: I appreciated her helping me", "ing: He delayed doing his taxes", "ing: He postponed returning to Paris",
//            "ing: She practised singing the song", "ing: She considered moving to New York", "ing: He can't stand her smoking in the office",
//            "ing: He can't help talking so loudly", "ing: He risked being caught", "ing: He admitted cheating on the test",
//            "ing: He denied committing the crime", "ing: He mentioned going to that college", "ing: He imagines working there one day",
//            "ing: I tolerated her talking", "ing: I understand his quitting", "ing: The job involves travelling to Japan once a month",
//            "ing: He completed renovating the house", "ing: He reported her stealing the money", "ing: I anticipated arriving late",
//            "ing: Tom recalled using his credit card at the store", "to+inf: She agreed to give a presentation at the meeting",
//            "to+inf: I asked him to leave early", "to+inf: We decided to go out for dinner", "to+inf: He helped to clean the kitchen",
//            "to+inf: She plans to buy a new flat next year", "to+inf: I hope to pass the exam", "to+inf: They are learning to sing",
//            "to+inf : I want him to come to the party", "to+inf: I would like to see her tonight", "to+inf: We promised not to be late",
//            "to+inf: We can't afford to go on holiday", "to+inf: He managed to open the door without the key", "to+inf: They prepared to take the test",
//            "to+inf: He demanded to speak to Mr. Harris", "to+inf: I chose to help", "to+inf: Frank offered to drive us to the supermarket",
//            "to+inf: She waited to buy a movie ticket", "to+inf: I'd hate to be late", "to+inf: I'd love to come", "to+inf: Nancy seemed to be disappointed",
//            "to+inf: they expect Julie to arrive early", "to+inf: We intend to visit you next spring", "to+inf: The child pretended to be a monster",
//            "to+inf: The guard refused to let them enter the building", "to+inf: He tends to be a little shy", "to+inf: I'd prefer him to do it",
//            "to+inf: He deserves to go to jail", "to+inf: His health appeared to be better", "to+inf: Naomi arranged to stay with her cousin in Miami",
//            "to+inf: She claimed to be a princess"));*/
//
//
//    private final List<String> wordsBased = new ArrayList<>(List.of("LOOK AFTER", "LOOK AROUND", "LOOK BACK ON",
//            "LOOK DOWN ON", "LOOK FOR", "LOOK FORWARD TO", "LOOK INTO", "LOOK ON", "LOOK OUT", "LOOK OVER", "LOOK UP",
//            "LOOK UP TO", "LOOK THROUHG", "LOOK IN ON"));
//            /*"enjoy", "fancy - хотіти", "discuss", "dislike", "finish",
//            "mind", "suggest", "recommend", "keep", "avoid", "miss", "appreciate", "delay", "postpone", "practice", "consider",
//            "can't stand", "can't help", "risk", "admit", "deny - заперечувати", "mention - згадати", "imagine", "tolerate", "understand", "involve - містить",
//            "complete", "report", "anticipate - передбачати", "recall - почати знову", "agree", "ask", "decide", "help", "plan", "hope", "learn", "want",
//            "would like", "promise", "can afford", "manage", "prepare", "demand - вимагати", "choose", "offer - пропонувати", "wait", "would hate",
//            "would love", "seem", "expect", "intend - мати намір", "pretend", "refuse - відмова", "tend - прагнути", "would prefer", "deserve", "appear", "arrange - домовлятися", "claim"));*/
//
//    private final List<String> correctWords = new ArrayList<>();
//    {
//        for (String word : correctWordsBased) {
//            correctWords.add(word);
//        }
//    }
//
//    private final List<String> words = new ArrayList<>();
//    {
//        for (String word : wordsBased) {
//            words.add(word);
//        }
//    }
//
////    private int wordIntForDelete = 0;
////    private int currentInt = 0;

    private final Map<Long, User> users = new HashMap<>();


    public VacanciesBot() {
        super("6599760212:AAEErnx7c8AGHl4obUvHIjv5vRxb3TrUY2E");
    }

    @Override
    public void onUpdateReceived(Update update) {

    }





    private void sendMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(Update update, String message, ReplyKeyboard keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        sendMessage.setText(message);
        sendMessage.setReplyMarkup(keyboard);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageCBD(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());

        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageCBD(Update update, String message, ReplyKeyboard keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());

        sendMessage.setText(message);
        sendMessage.setReplyMarkup(keyboard);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboard createKeyboard(List<String> names, List<String> backDatas) {
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton();
            nextButton.setText(names.get(i));
            nextButton.setCallbackData(backDatas.get(i));
            row.add(nextButton);
        }

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));
        return keyboard;
    }

    private ReplyKeyboard createKeyboardVerticle(List<String> names, List<String> backDatas) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton nextButton = new InlineKeyboardButton();
            nextButton.setText(names.get(i));
            nextButton.setCallbackData(backDatas.get(i));
            row.add(nextButton);
            rows.add(row);
        }

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton nextButton1 = new InlineKeyboardButton();
        nextButton1.setText("До головного меню");
        nextButton1.setCallbackData("toMainMenuCBD");
        row1.add(nextButton1);
        rows.add(row1);

        keyboard.setKeyboard(rows);

        return keyboard;
    }

    @Override
    public String getBotUsername() {
        return "@FlashMind_ua_bot";
    }
}
