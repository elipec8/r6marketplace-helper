package github.ricemonger.utilspostgresschema.ids.user;


import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utilspostgresschema.id_entities.user.IdTelegramUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserInputEntityId {
    private IdTelegramUserEntity telegramUser;
    private InputState inputState;

    public TelegramUserInputEntityId(String chatId, InputState inputState) {
        this.telegramUser = new IdTelegramUserEntity(chatId);
        this.inputState = inputState;
    }
}
