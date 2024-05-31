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





    private void playPackCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        users.get(chatId).setStan(6);

        String str = "Ось Ваші паки з флеш-картками. Оберіть один з них для продовження роботи";

        List<String> packsNames = users.get(chatId).getPacksNames();
        List<String> packsNamesCBD = IntStream.rangeClosed(0, packsNames.size()-1)
                .mapToObj(String::valueOf) // Перетворення чисел в рядки
                .collect(Collectors.toList());

        ReplyKeyboard keyboard = createKeyboardVerticle(packsNames, packsNamesCBD);

        sendMessageCBD(update, str, keyboard);
    }

    private void getChoosedPackCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        users.get(chatId).setStan(0);

        int choosed = Integer.parseInt(update.getCallbackQuery().getData());
        users.get(chatId).setChoosedPack(users.get(chatId).getPacks().get(choosed));

        System.out.println("choosed = " + choosed + " = " + users.get(chatId).getPacks().get(choosed).getName());

        String str = "Пак обрано! Картка надсилається..";

        sendMessageCBD(update, str);

        printFirstSideCard(update);
    }

    private void printFirstSideCard(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        if (users.get(chatId).getChoosedPack().size() <= 0) {
            String str1 = "Пак пустий! Картки відсутні! Спробуйте поновити його в налаштуваннях або додати нові картки" +
                    "\n\nВас повернено до головного меню";
            sendMessageCBD(update, str1);
            toMainMenuCBD(update);
            return;
        }

        users.get(chatId).getChoosedPack().next();

        String str = users.get(chatId).getChoosedPack().getFirstSide();



        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();

        InlineKeyboardButton nextButton1 = new InlineKeyboardButton();
        nextButton1.setText("Відповідь");
        nextButton1.setCallbackData("getSecondSideCBD");
        row1.add(nextButton1);

        InlineKeyboardButton nextButton2 = new InlineKeyboardButton();
        nextButton2.setText("Видалити");
        nextButton2.setCallbackData("removeCardCBD");
        row1.add(nextButton2);

        List<InlineKeyboardButton> row2 = new ArrayList<>();

        InlineKeyboardButton nextButton3 = new InlineKeyboardButton();
        nextButton3.setText("До головного меню");
        nextButton3.setCallbackData("toMainMenuCBD");
        row2.add(nextButton3);

        rows.add(row1);
        rows.add(row2);

        keyboard.setKeyboard(rows);



        sendMessageCBD(update, str, keyboard);
    }

    private void printSecondSideCard(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        String str = users.get(chatId).getChoosedPack().getSecondSide();



        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();

        InlineKeyboardButton nextButton1 = new InlineKeyboardButton();
        nextButton1.setText("Наступне");
        nextButton1.setCallbackData("getFirstSideCBD");
        row1.add(nextButton1);

        InlineKeyboardButton nextButton2 = new InlineKeyboardButton();
        nextButton2.setText("Видалити");
        nextButton2.setCallbackData("removeCardCBD");
        row1.add(nextButton2);

        List<InlineKeyboardButton> row2 = new ArrayList<>();

        InlineKeyboardButton nextButton3 = new InlineKeyboardButton();
        nextButton3.setText("До головного меню");
        nextButton3.setCallbackData("toMainMenuCBD");
        row2.add(nextButton3);

        rows.add(row1);
        rows.add(row2);

        keyboard.setKeyboard(rows);



        sendMessageCBD(update, str, keyboard);
    }

    private void deleteCardTempCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        String str = "Картка " + users.get(chatId).getChoosedPack().getFirstSide() + " . " +
                users.get(chatId).getChoosedPack().getSecondSide() + " видалена!" +
                "\nПоновити пак можливо в його налаштуваннях";

        users.get(chatId).getChoosedPack().removeCurTemp();

        sendMessageCBD(update, str);

        printFirstSideCard(update);
    }






    private void addPackCommand(Update update) {
        String str = "Введіть назву паку:";
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        users.get(chatId).setStan(2);
        System.out.println("stan==2");

        users.get(chatId).addPack(new PackCard());

        sendMessageCBD(update, str, createKeyboard(List.of("Скасувати додавання"), List.of("cancelAddingCBD")));
    }

    private void cancelAddingCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        users.get(chatId).setStan(0);
        System.out.println("stan==0");

        users.get(chatId).removeLastPack();

        String str1 = "Дію додавання відмінено! Вас повернено до головного меню";

        sendMessageCBD(update, str1);

        String str = "Привіт від FlashMind - першого телеграм-бота з флеш-картками. Створюй, поширюй, навчайся!";
        ReplyKeyboard keyboard = createKeyboard(List.of("Мої паки", "Додати пак", "Запустити пак"), List.of("myPacksCBD", "addPackCBD", "playPackCBD"));

        sendMessageCBD(update, str, keyboard);
    }

    private void addNameCommand(Update update) {
        Long chatId = update.getMessage().getChatId();
        users.get(chatId).setStan(0);

        String name = update.getMessage().getText().toString();

        for (User user: users.values()) {
            ArrayList<PackCard> packs = user.getPacks();
            for (PackCard curPack: packs) {
                if (name.equals(curPack.getName())) {
                    if (!curPack.isPrivate()) {
                        users.get(chatId).removeLastPack();
                        users.get(chatId).addPack(curPack.copy());

                        String str1 = "Пак знайдено серед існуючих! Додати його копію до ваших паків?";
                        ReplyKeyboard keyboard1 = createKeyboard(List.of("Так", "Ні", "Скасувати додавання"), List.of("yesAddExistCBD", "noAddExistCBD", "cancelAddingCBD"));

                        sendMessage(update, str1, keyboard1);
                        return;
                    }
                }
            }
        }

        users.get(chatId).getLastPack().setName(name);
        String str1 = "Тепер оберіть тип приватності (це визначатиме, чи можуть інші користувачі " +
                "знайти ваш пак за іменем і додати його копію до своїх)";
        ReplyKeyboard keyboard1 = createKeyboard(List.of("Приватний", "Публічний", "Скасувати додавання"), List.of("privacyTrueCBD", "privacyFalseCBD", "cancelAddingCBD"));
        sendMessage(update, str1, keyboard1);
    }

    private void yesAddExistCommand(Update update) {
        String str = "Пак додано! Вас повернено до головного меню";

        sendMessageCBD(update, str);

        startCommand(update);
    }

    private void noAddExistCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        users.get(chatId).removeLastPack();

        String str1 = "Тоді введіть нову назву, яка не співпадатиме з існуючими";

        sendMessageCBD(update, str1);

        addPackCommand(update);
    }

    private void setPrivacyCommand(Update update, boolean privacy) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        users.get(chatId).getPacks().get(users.get(chatId).getPacks().size()-1).setPrivacy(privacy);
        users.get(chatId).setStan(3);
        System.out.println("stan==3");

        String str = "Додайте першу картку! Введіть першу сторону картки і другу, розмежовуючи крапкою.  \"перше . друге\". " +
                "Можете вводити одразу кілька карток, розмежовуючи через Enter";

        sendMessageCBD(update, str, createKeyboard(List.of("Скасувати додавання"), List.of("cancelAddingCBD")));
    }

    private void addCardsCommand(Update update) {
        Long chatId = update.getMessage().getChatId();
        String str = "";

        String text = update.getMessage().getText();
        String[] cards = text.split("\\n");
        int size = 0;

        System.out.println(users.get(chatId).getLastPack().getName());

        for (int i = 0; i < cards.length; i++) {
            String[] parts = cards[i].split(" \\.");
            if (parts.length == 2) {
                users.get(chatId).getLastPack().add(parts[0].trim(), parts[1].trim());
                size++;
            } else {
                str = "Введено некоректний формат данних, спробуйте ще!";
                sendMessage(update, str, createKeyboard(List.of("Скасувати додавання"), List.of("cancelAddingCBD")));
                return;
            }
        }

        users.get(chatId).setStan(0);
        System.out.println("stan==0");
        str = "Додано " + size + " карточок! Продовжити?";
        ReplyKeyboard keyboard = createKeyboard(List.of("Так", "Ні", "Скасувати додавання"),
                List.of("yesAddMoreCardsCBD", "noAddMoreCardsCBD", "cancelAddingCBD"));
        sendMessage(update, str, keyboard);
    }

    private void yesAddMoreCardsCommand(Update update) {
        String str = "Введіть першу сторону картки і другу, розмежовуючи крапкою.  \"перше . друге\". " +
                "Можете вводити одразу кілька карток, розмежовуючи через Enter";

        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        users.get(chatId).setStan(3);
        System.out.println("stan==3");

        sendMessageCBD(update, str, createKeyboard(List.of("Скасувати додавання"), List.of("cancelAddingCBD")));
    }

    private void noAddMoreCardsCommand(Update update) {
        String str = "Картки додано! Вас повернено до головного меню";

        sendMessageCBD(update, str);

        startCommand(update);
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
