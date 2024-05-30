package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExceptionCommandListener {
    public void handleException(Exception e, UpdateInfo updateInfo) {
        log.error("Exception occurred while handling updateInfo-{}", updateInfo);
        e.printStackTrace();
    }
}
