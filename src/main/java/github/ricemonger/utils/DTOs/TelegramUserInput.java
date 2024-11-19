package github.ricemonger.utils.DTOs;

import github.ricemonger.telegramBot.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserInput {
    private String chatId;
    private InputState inputState;
    private String value;
}
