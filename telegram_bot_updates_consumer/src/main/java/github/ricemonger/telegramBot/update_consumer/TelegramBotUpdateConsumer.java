package github.ricemonger.telegramBot.update_consumer;


import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.listeners.UpdatesToListenersDistributor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TelegramBotUpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final UpdateInfoMapper updateInfoMapper;

    private final UpdatesToListenersDistributor updatesToListenersDistributor;

    @Override
    public void consume(Update update) {
        UpdateInfo updateInfo = updateInfoMapper.mapToUpdateInfo(update);

        updatesToListenersDistributor.distribute(updateInfo);
    }
}
