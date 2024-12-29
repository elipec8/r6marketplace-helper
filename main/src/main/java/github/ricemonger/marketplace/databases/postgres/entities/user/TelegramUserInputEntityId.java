package github.ricemonger.marketplace.databases.postgres.entities.user;


import github.ricemonger.utils.enums.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserInputEntityId {
    private TelegramUserEntity telegramUser;
    private InputState inputState;

    public TelegramUserInputEntityId(String chatId, InputState inputState) {
        this.telegramUser = new TelegramUserEntity();
        this.telegramUser.setChatId(chatId);
        this.inputState = inputState;
    }
}
