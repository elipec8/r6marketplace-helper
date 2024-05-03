package github.ricemonger.telegramBot.updateReceiver;

import github.ricemonger.marketplace.databases.neo4j.services.UserService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UpdateToUpdateInfoMapper {

    private final UserService userService;

    public UpdateInfo map(Update update) {
        UpdateInfo updateInfo = new UpdateInfo();

        updateInfo.setUpdateId(update.getUpdateId());
        updateInfo.setHasMessage(update.hasMessage());
        updateInfo.setHasCallBackQuery(update.hasCallbackQuery());

        if (update.hasMessage()) {
            updateInfo.setChatId(update.getMessage().getChatId());
            updateInfo.setMessageText(update.getMessage().getText());
        }
        if (update.hasCallbackQuery()) {
            updateInfo.setChatId(update.getCallbackQuery().getMessage().getChatId());
            updateInfo.setCallbackQueryData(update.getCallbackQuery().getData());
        }

        updateInfo.setInputState(userService.getUserInputStateOrNull(updateInfo.getChatId()));
        updateInfo.setInputGroup(userService.getUserInputGroupOrNull(updateInfo.getChatId()));

        if (updateInfo.getInputState() == null) {
            updateInfo.setInputState(InputState.BASE);
        }

        if (updateInfo.getInputGroup() == null) {
            updateInfo.setInputGroup(InputGroup.BASE);
        }

        return updateInfo;
    }
}
