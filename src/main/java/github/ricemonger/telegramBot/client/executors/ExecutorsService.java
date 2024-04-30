package github.ricemonger.telegramBot.client.executors;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.UpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutorsService {

    private final BotService botService;

    public <T extends AbstractBotCommandExecutor> void execute(Class<T> executor, UpdateInfo updateInfo) {
        try {
            T executorInstance = executor.getDeclaredConstructor().newInstance();
            executorInstance.initAndExecute(updateInfo, botService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
