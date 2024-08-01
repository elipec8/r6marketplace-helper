package github.ricemonger.telegramBot.executors;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.BotInnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutorsService {

    private final BotInnerService botInnerService;

    public <T extends AbstractBotCommandExecutor> void execute(Class<T> executor, UpdateInfo updateInfo) {
        try {
            T executorInstance = executor.getDeclaredConstructor().newInstance();
            executorInstance.initAndExecute(updateInfo, botInnerService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
