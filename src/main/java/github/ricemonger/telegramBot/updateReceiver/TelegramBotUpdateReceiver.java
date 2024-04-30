package github.ricemonger.telegramBot.updateReceiver;


import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.updateReceiver.listeners.UpdatesToListenersDistributor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TelegramBotUpdateReceiver implements LongPollingSingleThreadUpdateConsumer {

    private final UpdateToUpdateInfoMapper updateToUpdateInfoMapper;

    private final UpdatesToListenersDistributor updatesToListenersDistributor;

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            UpdateInfo updateInfo = updateToUpdateInfoMapper.map(update);

            chooseCommandToExecute(updateInfo);
        }
    }

    private void chooseCommandToExecute(UpdateInfo updateInfo) {
        updatesToListenersDistributor.distribute(updateInfo);
    }
}
