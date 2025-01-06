package github.ricemonger.utilspostgresschema.ids.user;


import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
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
        this.telegramUser = new TelegramUserEntity(chatId);
        this.inputState = inputState;
    }
}
