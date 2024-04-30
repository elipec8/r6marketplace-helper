package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatesToListenersDistributor {

    private final CallbackCommandListener callbackCommandListener;

    private final DirectCommandListener directCommandListener;

    private final InputCommandListener inputCommandListener;

    public void distribute(UpdateInfo updateInfo) {

    }
}
