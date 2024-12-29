package github.ricemonger.utilspostgresschema.entities.user;


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
}
