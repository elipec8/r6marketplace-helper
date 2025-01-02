package github.ricemonger.telegramBot.update_consumer;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.listeners.UpdatesToListenersDistributor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TelegramBotUpdateConsumerTest {
    @Autowired
    private TelegramBotUpdateConsumer telegramBotUpdateConsumer;
    @MockBean
    private UpdateInfoMapper updateInfoMapper;
    @MockBean
    private UpdatesToListenersDistributor updatesToListenersDistributor;

    @Test
    public void consume_should_handle_mapped_update_to_distributor() {
        Update update = new Update();
        UpdateInfo updateInfo = new UpdateInfo();
        when(updateInfoMapper.mapToUpdateInfo(update)).thenReturn(updateInfo);

        telegramBotUpdateConsumer.consume(update);

        verify(updateInfoMapper).mapToUpdateInfo(same(update));
        verify(updatesToListenersDistributor).distribute(same(updateInfo));
    }
}
