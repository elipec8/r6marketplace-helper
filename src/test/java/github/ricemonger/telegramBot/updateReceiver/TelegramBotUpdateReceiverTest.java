package github.ricemonger.telegramBot.updateReceiver;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.updateReceiver.listeners.UpdatesToListenersDistributor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TelegramBotUpdateReceiverTest {

    @MockBean
    private UpdateInfoMapper updateInfoMapper;

    @MockBean
    private UpdatesToListenersDistributor updatesToListenersDistributor;

    @Autowired
    private TelegramBotUpdateReceiver telegramBotUpdateReceiver;

    public static final Update UPDATE = new Update();

    public static final UpdateInfo UPDATE_INFO = new UpdateInfo();

    @Test
    public void consumeShouldCallUpdateToUpdateInfoMapperMap() {
        telegramBotUpdateReceiver.consume(UPDATE);

        verify(updateInfoMapper).map(same(UPDATE));
    }

    @Test
    public void consumeShouldHandleMappedUpdateInfoToUpdateDistributor() {
        when(updateInfoMapper.map(UPDATE)).thenReturn(UPDATE_INFO);
        telegramBotUpdateReceiver.consume(UPDATE);

        verify(updatesToListenersDistributor).distribute(same(UPDATE_INFO));
    }
}
