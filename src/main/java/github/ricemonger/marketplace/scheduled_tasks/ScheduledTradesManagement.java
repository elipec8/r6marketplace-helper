package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.UserService;
import github.ricemonger.telegramBot.client.TelegramBotClient;
import github.ricemonger.utils.dtos.ConfigTrades;
import github.ricemonger.utils.dtos.TradingUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTradesManagement {

    private final UserService userService;

    private final CommonValuesService commonValuesService;

    private final TelegramBotClient telegramBotClient;

    private final GraphQlClientService graphQlClientService;

    @Scheduled(fixedRate = 60 * 1000, initialDelay = 300 * 1000) // every 1m after 5m of delay
    public void manageAllUsersTrades() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<TradingUser> toManage = userService.getAllTradingUsers();

        for (TradingUser tradingUser : toManage) {
            managerUserTrades(tradingUser, configTrades);
        }
    }

    private void managerUserTrades(TradingUser tradingUser, ConfigTrades configTrades) {

    }
}
