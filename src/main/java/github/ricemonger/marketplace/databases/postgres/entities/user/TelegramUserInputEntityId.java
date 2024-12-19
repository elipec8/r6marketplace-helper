package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserInputEntityId {
    private TelegramUserEntity telegramUser;
    private InputState inputState;

    public TelegramUserInputEntityId(String chatId, Long userId, InputState inputState) {
        this(chatId, new UserEntity(userId), inputState);
    }

    public TelegramUserInputEntityId(String chatId, UserEntity userEntity, InputState inputState) {
        this.telegramUser = new TelegramUserEntity(chatId, userEntity);
        this.inputState = inputState;
    }

    public String getChatId() {
        return telegramUser.getChatId();
    }

    public Long getUserId() {
        return telegramUser.getUserId();
    }

    public int hashCode() {
        return telegramUser.getChatId().hashCode() + inputState.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TelegramUserInputEntityId telegramUserInputEntityId)) {
            return false;
        }
        if (this.hashCode() != telegramUserInputEntityId.hashCode()) {
            return false;
        }
        return telegramUserInputEntityId.telegramUser.getChatId().equals(telegramUser.getChatId()) && telegramUserInputEntityId.inputState.equals(inputState);
    }
}
