package ru.javacourse.sourcecodebot.telegram;

import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.javacourse.sourcecodebot.telegram.handlers.HelpHandler;

import java.util.HashMap;
import java.util.Map;

public class ServiceBot extends TelegramLongPollingBot {

    private Map<String, MessageHandler> handlers = new HashMap<>();

    private static final String UNKNOWN_COMMAND = "Неизвестная команда";

    public ServiceBot() {
        // TODO поправить этот колхоз пока никто не увидел

        handlers.put("/help", new HelpHandler());
    }

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {

        String userMessage = update.getMessage().getText();

        if (!StringUtils.isEmpty(userMessage)){
            try {
                MessageHandler handler = handlers.get(userMessage);
                if (handler != null) {
                    execute(handler.handle(update));
                } else {
                    sendDefaultMessage(update);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendDefaultMessage(Update update) throws Exception {
        SendMessage msg = new SendMessage();
        msg.enableMarkdown(true);
        msg.setChatId(update.getMessage().getChatId());
        msg.setText(UNKNOWN_COMMAND);
        execute(msg);
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "SourceCodeSchoolbot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     *
     * TODO: спрятать токен
     */
    @Override
    public String getBotToken() {
        return "866667038:AAF-59KcmNLo66Qk6KlTRcporb9UUVYjWj0";
    }
}
