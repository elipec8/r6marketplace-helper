package github.ricemonger.marketplace.services.DTOs;

import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserInputStateAndGroup {
    private String chatId;
    private InputState inputState;
    private InputGroup inputGroup;
}
