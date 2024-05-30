package github.ricemonger.utils.dtos;

import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUser {
    private String chatId;
    private InputState inputState;
    private InputGroup inputGroup;
    private boolean publicNotificationsEnabledFlag;

    public TelegramUser(Long chatId) {
        this.chatId = String.valueOf(chatId);
        this.inputState = InputState.BASE;
        this.inputGroup = InputGroup.BASE;
        this.publicNotificationsEnabledFlag = true;
    }
}
