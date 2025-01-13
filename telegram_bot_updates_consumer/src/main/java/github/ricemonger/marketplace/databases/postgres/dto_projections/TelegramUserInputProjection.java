package github.ricemonger.marketplace.databases.postgres.dto_projections;

import github.ricemonger.utils.enums.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserInputProjection {
    private String chatId;
    private InputState inputState;
    private String value;
}
