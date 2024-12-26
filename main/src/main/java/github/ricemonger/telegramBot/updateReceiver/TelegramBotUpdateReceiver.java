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

    private final UpdateInfoMapper updateInfoMapper;

    private final UpdatesToListenersDistributor updatesToListenersDistributor;

    @Override
    public void consume(Update update) {
        UpdateInfo updateInfo = updateInfoMapper.mapToUpdateInfo(update);

        updatesToListenersDistributor.distribute(updateInfo);
    }
}
