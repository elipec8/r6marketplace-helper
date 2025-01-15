package github.ricemonger.fast_sell_trade_manager.services.factories;

import github.ricemonger.fast_sell_trade_manager.services.DTOs.*;
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
    public void createFastSellTradeManagerCommandsForUser_should_return_expected_commands() {
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

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(48);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity.setItemId("leaveSellTradeItemId");
        leaveMedianPriceAndRarity.setMonthMedianPrice(49);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(cancelMedianPriceAndRarity, leaveMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 4;

        List<FastTradeManagerCommand> result = tradeManagementCommandsFactory.createFastSellTradeManagerCommandsForUser(user, sellTrades, items, medianPriceAndRarities, sellLimit, sellSlots);

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");

        FastTradeManagerCommand updateExpectedCommand = new FastTradeManagerCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_UPDATE, "updateSellTradeItemId", "updateSellTradeId", 98);
        FastTradeManagerCommand createExpectedCommand = new FastTradeManagerCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CREATE, "createNewPotentialTradeItemId", 50);
        FastTradeManagerCommand cancelExpectedCommand = new FastTradeManagerCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CANCEL, "cancelSellTradeItemId", "updateSellTradeId");

        System.out.println("Result:");
        result.forEach(System.out::println);

        assertEquals(3, result.size());
        assertTrue(result.contains(updateExpectedCommand));
        assertTrue(result.contains(createExpectedCommand));
        assertTrue(result.contains(cancelExpectedCommand));
    }

    @Test
    public void createFastSellTradeManagerCommandsForUser_should_return_expected_commands_if_too_many_potentialTrades() {
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

        ItemMedianPriceAndRarity cancelMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        cancelMedianPriceAndRarity.setItemId("cancelSellTradeItemId");
        cancelMedianPriceAndRarity.setMonthMedianPrice(48);

        ItemMedianPriceAndRarity leaveMedianPriceAndRarity = new ItemMedianPriceAndRarity();
        leaveMedianPriceAndRarity.setItemId("leaveSellTradeItemId");
        leaveMedianPriceAndRarity.setMonthMedianPrice(49);

        List<ItemMedianPriceAndRarity> medianPriceAndRarities = List.of(cancelMedianPriceAndRarity, leaveMedianPriceAndRarity);

        int sellLimit = 20;
        int sellSlots = 4;

        List<FastTradeManagerCommand> result = tradeManagementCommandsFactory.createFastSellTradeManagerCommandsForUser(user, sellTrades, items, medianPriceAndRarities, sellLimit, sellSlots);

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");

        FastTradeManagerCommand updateExpectedCommand = new FastTradeManagerCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_UPDATE, "updateSellTradeItemId", "updateSellTradeId", 98);
        FastTradeManagerCommand createExpectedCommand = new FastTradeManagerCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CREATE, "createNewPotentialTradeItemId", 50);
        FastTradeManagerCommand cancelExpectedCommand = new FastTradeManagerCommand(authorizationDTO, FastTradeManagerCommandType.SELL_ORDER_CANCEL, "cancelSellTradeItemId", "updateSellTradeId");

        System.out.println("Result:");
        result.forEach(System.out::println);

        assertEquals(3, result.size());
        assertTrue(result.contains(updateExpectedCommand));
        assertTrue(result.contains(createExpectedCommand));
        assertTrue(result.contains(cancelExpectedCommand));
    }
}