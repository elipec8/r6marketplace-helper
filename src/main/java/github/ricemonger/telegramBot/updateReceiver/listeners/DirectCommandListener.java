package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.credentials.CredentialsDirect;
import github.ricemonger.telegramBot.executors.help.HelpDirect;
import github.ricemonger.telegramBot.executors.start.StartDirect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectCommandListener {

    private final ExecutorsService executorsService;

    public void handleUpdate(UpdateInfo updateInfo) {

        String text = updateInfo.getMessageText();

        switch (text) {

            case "/start" -> executorsService.execute(StartDirect.class, updateInfo);

            case "/help" -> executorsService.execute(HelpDirect.class, updateInfo);

            case "/credentials" -> executorsService.execute(CredentialsDirect.class, updateInfo);

            case "/cancel" -> executorsService.execute(Cancel.class, updateInfo);

            default -> throw new IllegalStateException("Unexpected message text value: " + text);
        }

    }
}
