package github.ricemonger.fast_sell_trade_manager.scheduled_tasks;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.UbiAccountEntryService;
import github.ricemonger.fast_sell_trade_manager.services.UserFastSellTradesManager;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledOneUserFastSellTradeManager {
    private final UserFastSellTradesManager userFastSellTradesManager;

    private final CommonValuesService commonValuesService;
    private final UbiAccountEntryService ubiAccountEntryService;

    private int sellLimit;
    private int sellSlots;
    private FastSellManagedUser managedUser;
    private List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = new ArrayList<>();

    @Scheduled(fixedRateString = "${app.scheduling.management_submit.fixedRate}", initialDelayString = "${app.scheduling.management_submit.initialDelay}")
    public void createFastSellCommands() {
        userFastSellTradesManager.submitAsyncCreateCommandsTask(managedUser, itemsMedianPriceAndRarity, sellLimit, sellSlots);
    }

    @Scheduled(fixedRateString = "${app.scheduling.management_execute.fixedRate}", initialDelayString = "${app.scheduling.management_execute.initialDelay}")
    public void executeFastSellCommands() {
        userFastSellTradesManager.executeFastSellCommands();
    }

    @Scheduled(fixedRateString = "${app.scheduling.keep_unused_slot.fixedRate}", initialDelayString = "${app.scheduling.keep_unused_slot.initialDelay}")
    public void keepUnusedOneSellSlotForManagedUser() {
        userFastSellTradesManager.createAndExecuteCommandsToKeepOneSellSlotUnused(managedUser, itemsMedianPriceAndRarity, sellLimit, sellSlots);
    }

    @Scheduled(fixedRateString = "${app.scheduling.median_prices_fetch.fixedRate}", initialDelayString = "${app.scheduling.median_prices_fetch.initialDelay}")
    public void fetchItemMedianPriceAndConfigTradesFromDb() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();
        sellLimit = configTrades.getSellLimit();
        sellSlots = configTrades.getSellSlots();
        itemsMedianPriceAndRarity = ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity(managedUser.getUbiProfileId());
    }

    @Scheduled(fixedRateString = "${app.scheduling.user_fetch.fixedRate}", initialDelayString = "${app.scheduling.user_fetch.initialDelay}")
    public void fetchManagedUserAuthorizationFromDb() {
        managedUser = ubiAccountEntryService.getFastSellManagedUserById(commonValuesService.getFastSellManagedUserId(), commonValuesService.getFastSellManagedUserEmail());
    }
}
