package github.ricemonger.fast_sell_trade_manager.services.factories;

import github.ricemonger.fast_sell_trade_manager.services.DTOs.*;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.DTOs.personal.SellTrade;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TradeManagementCommandsFactoryTest {
    @Autowired
    private TradeManagementCommandsFactory tradeManagementCommandsFactory;

    @Test
    public void createFastSellCommandsForUser_should_return_expected_commands() {
        FastSellManagedUser user = new FastSellManagedUser();
        user.setUbiProfileId("profileId");
        user.setResaleLocks(List.of("lockedItemId1"));
        user.setSoldIn24h(16);

        SellTrade updateSellTrade = new SellTrade();
        updateSellTrade.setItemId("updateSellTradeItemId");
        updateSellTrade.setTradeId("updateSellTradeId");
        updateSellTrade.setPrice(100);

        SellTrade cancelSellTrade = new SellTrade();
        cancelSellTrade.setItemId("cancelSellTradeItemId");
        cancelSellTrade.setTradeId("cancelSellTradeId");
        cancelSellTrade.setPrice(50);

        SellTrade alreadySameSellTrade = new SellTrade();
        alreadySameSellTrade.setItemId("alreadySameSellTradeItemId");
        alreadySameSellTrade.setTradeId("alreadySameSellTradeId");
        alreadySameSellTrade.setPrice(50);

        SellTrade leaveSellTrade = new SellTrade();
        leaveSellTrade.setItemId("leaveSellTradeItemId");
        leaveSellTrade.setTradeId("leaveSellTradeId");
        leaveSellTrade.setPrice(50);

        List<SellTrade> sellTrades = List.of(updateSellTrade, cancelSellTrade, alreadySameSellTrade, leaveSellTrade);

        PotentialTrade updateExistingPotentialTrade = new PotentialTrade();
        updateExistingPotentialTrade.setItemId("updateSellTradeItemId");
        updateExistingPotentialTrade.setPrice(98);
        updateExistingPotentialTrade.setMonthMedianPriceDifference(2);
        updateExistingPotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade createNewPotentialTrade = new PotentialTrade();
        createNewPotentialTrade.setItemId("createNewPotentialTradeItemId");
        createNewPotentialTrade.setPrice(50);
        createNewPotentialTrade.setMonthMedianPriceDifference(2);
        createNewPotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade alreadySamePotentialTrade = new PotentialTrade();
        alreadySamePotentialTrade.setItemId("alreadySameSellTradeItemId");
        alreadySamePotentialTrade.setPrice(49);
        alreadySamePotentialTrade.setMonthMedianPriceDifference(2);
        alreadySamePotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade skipCauseResaleLockPotentialTrade = new PotentialTrade();
        skipCauseResaleLockPotentialTrade.setItemId("lockedItemId1");
        skipCauseResaleLockPotentialTrade.setPrice(1000);
        skipCauseResaleLockPotentialTrade.setMonthMedianPriceDifference(200);
        skipCauseResaleLockPotentialTrade.setMonthMedianPriceDifferencePercentage(200);

        List<PotentialTrade> items = List.of(updateExistingPotentialTrade, createNewPotentialTrade, alreadySamePotentialTrade, skipCauseResaleLockPotentialTrade);

        List<ItemCurrentPrices> currentPrices = List.of();

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(48);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity.setItemId("leaveSellTradeItemId");
        leaveMedianPriceAndRarity.setMonthMedianPrice(49);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(cancelMedianPriceAndRarity, leaveMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 4;

        List<FastSellCommand> result = tradeManagementCommandsFactory.createFastSellCommandsForUser(user, sellTrades, currentPrices, medianPriceAndRarities, items, sellLimit, sellSlots);

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");

        FastSellCommand updateExpectedCommand = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_UPDATE, "updateSellTradeItemId", "updateSellTradeId", 98);
        FastSellCommand createExpectedCommand = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CREATE, "createNewPotentialTradeItemId", 50);
        FastSellCommand cancelExpectedCommand = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CANCEL, "cancelSellTradeItemId", "cancelSellTradeId");

        System.out.println("Result:");
        result.forEach(System.out::println);

        assertEquals(3, result.size());
        assertTrue(result.contains(updateExpectedCommand));
        assertTrue(result.contains(createExpectedCommand));
        assertTrue(result.contains(cancelExpectedCommand));
    }

    @Test
    public void createFastSellCommandsForUser_should_return_expected_commands_if_too_many_potentialTrades() {
        FastSellManagedUser user = new FastSellManagedUser();
        user.setUbiProfileId("profileId");
        user.setResaleLocks(List.of("lockedItemId1"));
        user.setSoldIn24h(19);

        SellTrade updateSellTrade = new SellTrade();
        updateSellTrade.setItemId("updateSellTradeItemId");
        updateSellTrade.setTradeId("updateSellTradeId");
        updateSellTrade.setPrice(100);

        SellTrade cancelSellTrade = new SellTrade();
        cancelSellTrade.setItemId("cancelSellTradeItemId");
        cancelSellTrade.setTradeId("cancelSellTradeId");
        cancelSellTrade.setPrice(50);

        SellTrade alreadySameSellTrade = new SellTrade();
        alreadySameSellTrade.setItemId("alreadySameSellTradeItemId");
        alreadySameSellTrade.setTradeId("alreadySameSellTradeId");
        alreadySameSellTrade.setPrice(50);

        SellTrade leaveSellTrade = new SellTrade();
        leaveSellTrade.setItemId("leaveSellTradeItemId");
        leaveSellTrade.setTradeId("leaveSellTradeId");
        leaveSellTrade.setPrice(50);

        List<SellTrade> sellTrades = List.of(updateSellTrade, cancelSellTrade, alreadySameSellTrade, leaveSellTrade);

        PotentialTrade updateExistingPotentialTrade = new PotentialTrade();
        updateExistingPotentialTrade.setItemId("updateSellTradeItemId");
        updateExistingPotentialTrade.setPrice(98);
        updateExistingPotentialTrade.setMonthMedianPriceDifference(2);
        updateExistingPotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade createNewPotentialTrade = new PotentialTrade();
        createNewPotentialTrade.setItemId("createNewPotentialTradeItemId");
        createNewPotentialTrade.setPrice(50);
        createNewPotentialTrade.setMonthMedianPriceDifference(2);
        createNewPotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade createNewPotentialTradeByBuyPrice = new PotentialTrade();
        createNewPotentialTradeByBuyPrice.setItemId("createNewPotentialTradeByBuyPriceItemId");
        createNewPotentialTradeByBuyPrice.setPrice(1);
        createNewPotentialTradeByBuyPrice.setMonthMedianPriceDifference(1);
        createNewPotentialTradeByBuyPrice.setMonthMedianPriceDifferencePercentage(1);
        createNewPotentialTradeByBuyPrice.setSellByMaxBuyPrice(true);

        PotentialTrade createNewPotentialTradeLowerProfit = new PotentialTrade();
        createNewPotentialTradeLowerProfit.setItemId("createNewPotentialTradeLowerProfitItemId");
        createNewPotentialTradeLowerProfit.setPrice(50);
        createNewPotentialTradeLowerProfit.setMonthMedianPriceDifference(1);
        createNewPotentialTradeLowerProfit.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade alreadySamePotentialTrade = new PotentialTrade();
        alreadySamePotentialTrade.setItemId("alreadySameSellTradeItemId");
        alreadySamePotentialTrade.setPrice(49);
        alreadySamePotentialTrade.setMonthMedianPriceDifference(2);
        alreadySamePotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade skipCauseResaleLockPotentialTrade = new PotentialTrade();
        skipCauseResaleLockPotentialTrade.setItemId("lockedItemId1");
        skipCauseResaleLockPotentialTrade.setPrice(1000);
        skipCauseResaleLockPotentialTrade.setMonthMedianPriceDifference(200);
        skipCauseResaleLockPotentialTrade.setMonthMedianPriceDifferencePercentage(200);

        List<PotentialTrade> items = List.of(updateExistingPotentialTrade, createNewPotentialTrade, alreadySamePotentialTrade,
                skipCauseResaleLockPotentialTrade, createNewPotentialTradeLowerProfit, createNewPotentialTradeByBuyPrice);

        ItemCurrentPrices cancelCurrentPrices = new ItemCurrentPrices();
        cancelCurrentPrices.setItemId("cancelSellTradeItemId");
        cancelCurrentPrices.setMinSellPrice(49);

        ItemCurrentPrices leaveCurrentPrices = new ItemCurrentPrices();
        leaveCurrentPrices.setItemId("leaveSellTradeItemId");
        leaveCurrentPrices.setMinSellPrice(48);

        List<ItemCurrentPrices> currentPrices = List.of(cancelCurrentPrices, leaveCurrentPrices);

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(50);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity.setItemId("leaveSellTradeItemId");
        leaveMedianPriceAndRarity.setMonthMedianPrice(47);

        ItemMedianPriceAndRarity updateMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        updateMedianPriceAndRarity.setItemId("updateSellTradeItemId");
        updateMedianPriceAndRarity.setMonthMedianPrice(90);

        ItemMedianPriceAndRarity alreadySameMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        alreadySameMedianPriceAndRarity.setItemId("alreadySameSellTradeItemId");
        alreadySameMedianPriceAndRarity.setMonthMedianPrice(40);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(cancelMedianPriceAndRarity, leaveMedianPriceAndRarity, updateMedianPriceAndRarity, alreadySameMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 4;

        List<FastSellCommand> result = tradeManagementCommandsFactory.createFastSellCommandsForUser(user, sellTrades, currentPrices, medianPriceAndRarities, items, sellLimit, sellSlots);

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");

        FastSellCommand updateExpectedCommand = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_UPDATE, "updateSellTradeItemId", "updateSellTradeId", 98);
        FastSellCommand createExpectedCommand = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CREATE, "createNewPotentialTradeItemId", 50);
        FastSellCommand createExpectedCommandByBuyPrice = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CREATE, "createNewPotentialTradeByBuyPriceItemId", 1);
        FastSellCommand cancelExpectedCommand = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CANCEL, "cancelSellTradeItemId", "cancelSellTradeId");
        FastSellCommand cancelLeftExpectedCommand = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CANCEL, "leaveSellTradeItemId", "leaveSellTradeId");

        System.out.println("Result:");
        result.forEach(System.out::println);

        assertEquals(5, result.size());
        assertTrue(result.contains(updateExpectedCommand));
        assertTrue(result.contains(createExpectedCommand));
        assertTrue(result.contains(createExpectedCommandByBuyPrice));
        assertTrue(result.contains(cancelExpectedCommand));
        assertTrue(result.contains(cancelLeftExpectedCommand));
    }

    @Test
    public void createFastSellCommandsForUser_should_return_only_update_list_if_day_limit_exceeded() {
        FastSellManagedUser user = new FastSellManagedUser();
        user.setUbiProfileId("profileId");
        user.setResaleLocks(List.of("lockedItemId1"));
        user.setSoldIn24h(20);

        SellTrade updateSellTrade = new SellTrade();
        updateSellTrade.setItemId("updateSellTradeItemId");
        updateSellTrade.setTradeId("updateSellTradeId");
        updateSellTrade.setPrice(100);

        SellTrade cancelSellTrade = new SellTrade();
        cancelSellTrade.setItemId("cancelSellTradeItemId");
        cancelSellTrade.setTradeId("cancelSellTradeId");
        cancelSellTrade.setPrice(50);

        SellTrade alreadySameSellTrade = new SellTrade();
        alreadySameSellTrade.setItemId("alreadySameSellTradeItemId");
        alreadySameSellTrade.setTradeId("alreadySameSellTradeId");
        alreadySameSellTrade.setPrice(50);

        SellTrade leaveSellTrade = new SellTrade();
        leaveSellTrade.setItemId("leaveSellTradeItemId");
        leaveSellTrade.setTradeId("leaveSellTradeId");
        leaveSellTrade.setPrice(50);

        List<SellTrade> sellTrades = List.of(updateSellTrade, cancelSellTrade, alreadySameSellTrade, leaveSellTrade);

        PotentialTrade updateExistingPotentialTrade = new PotentialTrade();
        updateExistingPotentialTrade.setItemId("updateSellTradeItemId");
        updateExistingPotentialTrade.setPrice(98);
        updateExistingPotentialTrade.setMonthMedianPriceDifference(2);
        updateExistingPotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade createNewPotentialTrade = new PotentialTrade();
        createNewPotentialTrade.setItemId("createNewPotentialTradeItemId");
        createNewPotentialTrade.setPrice(50);
        createNewPotentialTrade.setMonthMedianPriceDifference(2);
        createNewPotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade createNewPotentialTradeLowerProfit = new PotentialTrade();
        createNewPotentialTradeLowerProfit.setItemId("createNewPotentialTradeLowerProfitItemId");
        createNewPotentialTradeLowerProfit.setPrice(50);
        createNewPotentialTradeLowerProfit.setMonthMedianPriceDifference(1);
        createNewPotentialTradeLowerProfit.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade alreadySamePotentialTrade = new PotentialTrade();
        alreadySamePotentialTrade.setItemId("alreadySameSellTradeItemId");
        alreadySamePotentialTrade.setPrice(49);
        alreadySamePotentialTrade.setMonthMedianPriceDifference(2);
        alreadySamePotentialTrade.setMonthMedianPriceDifferencePercentage(2);

        PotentialTrade skipCauseResaleLockPotentialTrade = new PotentialTrade();
        skipCauseResaleLockPotentialTrade.setItemId("lockedItemId1");
        skipCauseResaleLockPotentialTrade.setPrice(1000);
        skipCauseResaleLockPotentialTrade.setMonthMedianPriceDifference(200);
        skipCauseResaleLockPotentialTrade.setMonthMedianPriceDifferencePercentage(200);

        List<PotentialTrade> items = List.of(updateExistingPotentialTrade, createNewPotentialTrade, alreadySamePotentialTrade,
                skipCauseResaleLockPotentialTrade, createNewPotentialTradeLowerProfit);

        List<ItemCurrentPrices> currentPrices = List.of();

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(48);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity.setItemId("leaveSellTradeItemId");
        leaveMedianPriceAndRarity.setMonthMedianPrice(49);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(cancelMedianPriceAndRarity, leaveMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 4;

        List<FastSellCommand> result = tradeManagementCommandsFactory.createFastSellCommandsForUser(user, sellTrades, currentPrices, medianPriceAndRarities, items, sellLimit, sellSlots);

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");

        FastSellCommand updateExpectedCommand = new FastSellCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_UPDATE, "updateSellTradeItemId", "updateSellTradeId", 98);

        System.out.println("Result:");
        result.forEach(System.out::println);

        assertEquals(1, result.size());
        assertTrue(result.contains(updateExpectedCommand));
    }

    @Test
    public void createKeepUnusedSlotCommandForUser_should_return_expected_commands_by_median_prices() {
        SellTrade leaveSellTrade1 = new SellTrade();
        leaveSellTrade1.setItemId("leaveSellTradeItemId1");
        leaveSellTrade1.setTradeId("leaveSellTradeId1");
        leaveSellTrade1.setPrice(50);

        SellTrade leaveSellTrade2 = new SellTrade();
        leaveSellTrade2.setItemId("leaveSellTradeItemId2");
        leaveSellTrade2.setTradeId("leaveSellTradeId2");
        leaveSellTrade2.setPrice(50);

        SellTrade cancelSellTrade = new SellTrade();
        cancelSellTrade.setItemId("cancelSellTradeItemId");
        cancelSellTrade.setTradeId("cancelSellTradeId");
        cancelSellTrade.setPrice(50);

        FastSellManagedUser user = new FastSellManagedUser();
        user.setUbiProfileId("profileId");
        user.setSoldIn24h(19);

        ItemCurrentPrices leaveCurrentPrices1 = new ItemCurrentPrices();
        leaveCurrentPrices1.setItemId("leaveSellTradeItemId1");
        leaveCurrentPrices1.setMinSellPrice(50);

        List<ItemCurrentPrices> currentPrices = List.of(leaveCurrentPrices1);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity2 = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity2.setItemId("leaveSellTradeItemId2");
        leaveMedianPriceAndRarity2.setMonthMedianPrice(15);

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(35);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(leaveMedianPriceAndRarity2, cancelMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 3;

        List<FastSellCommand> result = tradeManagementCommandsFactory.createKeepUnusedSlotCommandForUser(user, List.of(leaveSellTrade1, leaveSellTrade2, cancelSellTrade), currentPrices, medianPriceAndRarities, sellLimit, sellSlots);

        assertEquals(1, result.size());
        assertEquals(new FastSellCommand(user.toAuthorizationDTO(), FastTradeManagerCommandType.SELL_ORDER_CANCEL, "cancelSellTradeItemId", "cancelSellTradeId"), result.get(0));
    }

    @Test
    public void createKeepUnusedSlotCommandForUser_should_return_expected_commands_by_current_prices() {
        SellTrade leaveSellTrade1 = new SellTrade();
        leaveSellTrade1.setItemId("leaveSellTradeItemId1");
        leaveSellTrade1.setTradeId("leaveSellTradeId1");
        leaveSellTrade1.setPrice(50);

        SellTrade leaveSellTrade2 = new SellTrade();
        leaveSellTrade2.setItemId("leaveSellTradeItemId2");
        leaveSellTrade2.setTradeId("leaveSellTradeId2");
        leaveSellTrade2.setPrice(50);

        SellTrade cancelSellTrade = new SellTrade();
        cancelSellTrade.setItemId("cancelSellTradeItemId");
        cancelSellTrade.setTradeId("cancelSellTradeId");
        cancelSellTrade.setPrice(50);

        FastSellManagedUser user = new FastSellManagedUser();
        user.setUbiProfileId("profileId");
        user.setSoldIn24h(19);

        ItemCurrentPrices leaveCurrentPrices1 = new ItemCurrentPrices();
        leaveCurrentPrices1.setItemId("leaveSellTradeItemId1");
        leaveCurrentPrices1.setMinSellPrice(50);

        ItemCurrentPrices leaveCurrentPrices2 = new ItemCurrentPrices();
        leaveCurrentPrices2.setItemId("leaveSellTradeItemId2");
        leaveCurrentPrices2.setMinSellPrice(50);

        ItemCurrentPrices cancelCurrentPrices = new ItemCurrentPrices();
        cancelCurrentPrices.setItemId("cancelSellTradeItemId");
        cancelCurrentPrices.setMinSellPrice(49);

        List<ItemCurrentPrices> currentPrices = List.of(leaveCurrentPrices1, leaveCurrentPrices2, cancelCurrentPrices);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity2 = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity2.setItemId("leaveSellTradeItemId2");
        leaveMedianPriceAndRarity2.setMonthMedianPrice(35);

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(15);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(leaveMedianPriceAndRarity2, cancelMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 3;

        List<FastSellCommand> result = tradeManagementCommandsFactory.createKeepUnusedSlotCommandForUser(user, List.of(leaveSellTrade1, leaveSellTrade2, cancelSellTrade), currentPrices, medianPriceAndRarities, sellLimit, sellSlots);

        assertEquals(1, result.size());
        assertEquals(new FastSellCommand(user.toAuthorizationDTO(), FastTradeManagerCommandType.SELL_ORDER_CANCEL, "cancelSellTradeItemId", "cancelSellTradeId"), result.get(0));
    }

    @Test
    public void createKeepUnusedSlotCommandForUser_should_return_empty_if_slots_available() {
        SellTrade leaveSellTrade1 = new SellTrade();
        leaveSellTrade1.setItemId("leaveSellTradeItemId1");
        leaveSellTrade1.setTradeId("leaveSellTradeId1");
        leaveSellTrade1.setPrice(50);

        SellTrade leaveSellTrade2 = new SellTrade();
        leaveSellTrade2.setItemId("leaveSellTradeItemId2");
        leaveSellTrade2.setTradeId("leaveSellTradeId2");
        leaveSellTrade2.setPrice(50);

        SellTrade cancelSellTrade = new SellTrade();
        cancelSellTrade.setItemId("cancelSellTradeItemId");
        cancelSellTrade.setTradeId("cancelSellTradeId");
        cancelSellTrade.setPrice(50);

        FastSellManagedUser user = new FastSellManagedUser();
        user.setUbiProfileId("profileId");
        user.setSoldIn24h(19);

        ItemCurrentPrices leaveCurrentPrices1 = new ItemCurrentPrices();
        leaveCurrentPrices1.setItemId("leaveSellTradeItemId1");
        leaveCurrentPrices1.setMinSellPrice(50);

        ItemCurrentPrices leaveCurrentPrices2 = new ItemCurrentPrices();
        leaveCurrentPrices2.setItemId("leaveSellTradeItemId2");
        leaveCurrentPrices2.setMinSellPrice(50);

        ItemCurrentPrices cancelCurrentPrices = new ItemCurrentPrices();
        cancelCurrentPrices.setItemId("cancelSellTradeItemId");
        cancelCurrentPrices.setMinSellPrice(49);

        List<ItemCurrentPrices> currentPrices = List.of(leaveCurrentPrices1, leaveCurrentPrices2, cancelCurrentPrices);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity2 = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity2.setItemId("leaveSellTradeItemId2");
        leaveMedianPriceAndRarity2.setMonthMedianPrice(35);

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(15);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(leaveMedianPriceAndRarity2, cancelMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 4;

        List<FastSellCommand> result = tradeManagementCommandsFactory.createKeepUnusedSlotCommandForUser(user, List.of(leaveSellTrade1, leaveSellTrade2, cancelSellTrade), currentPrices, medianPriceAndRarities, sellLimit, sellSlots);

        assertEquals(0, result.size());
    }

    @Test
    public void createKeepUnusedSlotCommandForUser_should_return_empty_if_sell_limit_exceeded() {
        SellTrade leaveSellTrade1 = new SellTrade();
        leaveSellTrade1.setItemId("leaveSellTradeItemId1");
        leaveSellTrade1.setTradeId("leaveSellTradeId1");
        leaveSellTrade1.setPrice(50);

        SellTrade leaveSellTrade2 = new SellTrade();
        leaveSellTrade2.setItemId("leaveSellTradeItemId2");
        leaveSellTrade2.setTradeId("leaveSellTradeId2");
        leaveSellTrade2.setPrice(50);

        SellTrade cancelSellTrade = new SellTrade();
        cancelSellTrade.setItemId("cancelSellTradeItemId");
        cancelSellTrade.setTradeId("cancelSellTradeId");
        cancelSellTrade.setPrice(50);

        FastSellManagedUser user = new FastSellManagedUser();
        user.setUbiProfileId("profileId");
        user.setSoldIn24h(20);

        ItemCurrentPrices leaveCurrentPrices1 = new ItemCurrentPrices();
        leaveCurrentPrices1.setItemId("leaveSellTradeItemId1");
        leaveCurrentPrices1.setMinSellPrice(50);

        ItemCurrentPrices leaveCurrentPrices2 = new ItemCurrentPrices();
        leaveCurrentPrices2.setItemId("leaveSellTradeItemId2");
        leaveCurrentPrices2.setMinSellPrice(50);

        ItemCurrentPrices cancelCurrentPrices = new ItemCurrentPrices();
        cancelCurrentPrices.setItemId("cancelSellTradeItemId");
        cancelCurrentPrices.setMinSellPrice(49);

        List<ItemCurrentPrices> currentPrices = List.of(leaveCurrentPrices1, leaveCurrentPrices2, cancelCurrentPrices);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity2 = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity2.setItemId("leaveSellTradeItemId2");
        leaveMedianPriceAndRarity2.setMonthMedianPrice(35);

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(15);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(leaveMedianPriceAndRarity2, cancelMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 3;

        List<FastSellCommand> result = tradeManagementCommandsFactory.createKeepUnusedSlotCommandForUser(user, List.of(leaveSellTrade1, leaveSellTrade2, cancelSellTrade), currentPrices, medianPriceAndRarities, sellLimit, sellSlots);

        assertEquals(0, result.size());
    }
}