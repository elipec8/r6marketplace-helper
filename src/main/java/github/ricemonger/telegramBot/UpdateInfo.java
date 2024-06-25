package github.ricemonger.telegramBot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfo {

    private Integer updateId;
    private Long chatId;


    private boolean hasMessage;
    private String messageText;


    private boolean hasCallBackQuery;
    private String callbackQueryData;

    private InputState inputState;
    private InputGroup inputGroup;
}
