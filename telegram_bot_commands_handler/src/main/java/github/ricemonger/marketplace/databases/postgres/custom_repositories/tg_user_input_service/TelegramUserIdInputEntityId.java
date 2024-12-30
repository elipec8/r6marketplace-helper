package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service;


import github.ricemonger.utils.enums.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserIdInputEntityId {
    private TelegramUserIdEntity telegramUser;
    private InputState inputState;

    public TelegramUserIdInputEntityId(String chatId, InputState inputState) {
        this.telegramUser = new TelegramUserIdEntity();
        this.telegramUser.setChatId(chatId);
        this.inputState = inputState;
    }
}
