package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.UserService;
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

    @Scheduled(fixedRate = 60 * 1000, initialDelay = 120 * 1000) // every 1m after 2m of delay
    public void manageAllUsersTrades() {
     //   int maxBuyTrades = commonValuesService.getMaxBuyTrades();

      //  int maxSellTrades = commonValuesService.getMaxSellTrades();

        List<TradingUser> toManage = userService.getAllTradingUsers();

        for (TradingUser user : toManage) {
       //     managerUserTrades(user, maxBuyTrades, maxSellTrades);
        }
    }

    private void managerUserTrades(TradingUser user, int maxBuyTrades, int maxSellTrades) {
      //  int activeBuyTrades = user.getActiveBuyTrades();
      //  int activeSellTrades = user.getActiveSellTrades();
    }
}
