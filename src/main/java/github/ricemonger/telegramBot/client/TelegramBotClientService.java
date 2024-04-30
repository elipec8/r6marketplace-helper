package github.ricemonger.telegramBot.client;

import github.ricemonger.telegramBot.UpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramBotClientService {

    private final TelegramBotClient telegramBotClient;

    public void askFromInlineKeyboard(UpdateInfo updateInfo, String text, int buttonsInLine, CallbackButton... buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = createInlineKeyboardMarkup(buttonsInLine, buttons);

        SendMessage sendMessage = new SendMessage(text, String.valueOf(updateInfo.getChatId()));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        executeMessageOnBot(sendMessage);
    }
    private InlineKeyboardMarkup createInlineKeyboardMarkup(int buttonsInLine,
                                                            CallbackButton... callbackButtons) {
        List<InlineKeyboardButton> inlineButtonsList = new ArrayList<>();
        List<InlineKeyboardRow> inlineRowsList = new ArrayList<>();

        int counter = 0;
        for (CallbackButton button : callbackButtons) {
            counter++;
            InlineKeyboardButton inlineButton = new InlineKeyboardButton(button.text());
            inlineButton.setCallbackData(button.data());
            inlineButtonsList.add(inlineButton);
            if (counter % buttonsInLine == 0) {
                inlineRowsList.add(new InlineKeyboardRow(inlineButtonsList));
                inlineButtonsList.clear();
            }
        }
        if (!inlineButtonsList.isEmpty()) {
            inlineRowsList.add(new InlineKeyboardRow(inlineButtonsList));
        }

        return new InlineKeyboardMarkup(inlineRowsList);
    }

    public void sendText(UpdateInfo updateInfo, String answer) {
        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), answer);
        executeMessageOnBot(sendMessage);
    }

    public void executeMessageOnBot(SendMessage sendMessage){
        try {
            telegramBotClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
