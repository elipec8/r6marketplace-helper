package github.ricemonger.trades_manager.services.factories;

import github.ricemonger.trades_manager.services.DTOs.*;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PrioritizedPotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TradeManagerCommandsFactoryTest {
    @Autowired
    private TradeManagerCommandsFactory tradeManagerCommandsFactory;

    @Test
    public void createCommandsForCentralTradeManagersForUser_should_return_expected_list_of_commands_if_Buy_trades_limit_exceeded() {
        Long userId = 1L;
        AuthorizationDTO authorizationDTO = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "rememberDeviceTicket", "rememberMeTicket");

        PersonalItem personalItemSellTradeCreate = new PersonalItem();
        personalItemSellTradeCreate.setItem(new Item("1"));
        personalItemSellTradeCreate.getItem().setName("name");
        personalItemSellTradeCreate.setTradeAlreadyExists(false);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsSellTradeCreate = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsSellTradeCreate.setPrice(1);
        PotentialPersonalSellTrade potentialSellTradeCreate = new PotentialPersonalSellTrade(personalItemSellTradeCreate, prioritizedPotentialTradeStatsSellTradeCreate);

        PersonalItem personalItemSellTradeUpdate = new PersonalItem();
        personalItemSellTradeUpdate.setItem(new Item("2"));
        personalItemSellTradeUpdate.getItem().setName("name2");
        personalItemSellTradeUpdate.setTradeAlreadyExists(true);
        personalItemSellTradeUpdate.setExistingTrade(new UbiTrade());
        personalItemSellTradeUpdate.getExistingTrade().setTradeId("tradeId2");
        personalItemSellTradeUpdate.getExistingTrade().setProposedPaymentPrice(2);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsSellTradeUpdate = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsSellTradeUpdate.setPrice(20);
        PotentialPersonalSellTrade potentialSellTradeUpdate = new PotentialPersonalSellTrade(personalItemSellTradeUpdate, prioritizedPotentialTradeStatsSellTradeUpdate);

        PersonalItem personalItemSellTradeLeave = new PersonalItem();
        personalItemSellTradeLeave.setItem(new Item("3"));
        personalItemSellTradeLeave.getItem().setName("name3");
        personalItemSellTradeLeave.setTradeAlreadyExists(true);
        personalItemSellTradeLeave.setExistingTrade(new UbiTrade());
        personalItemSellTradeLeave.getExistingTrade().setTradeId("tradeId3");
        personalItemSellTradeLeave.getExistingTrade().setProposedPaymentPrice(3);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsSellTradeLeave = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsSellTradeLeave.setPrice(3);
        PotentialPersonalSellTrade potentialSellTradeLeave = new PotentialPersonalSellTrade(personalItemSellTradeLeave, prioritizedPotentialTradeStatsSellTradeLeave);

        Collection<PotentialPersonalSellTrade> resultingSellTrades = List.of(potentialSellTradeCreate, potentialSellTradeUpdate, potentialSellTradeLeave);

        PrioritizedUbiTrade currentSellPrioritizedUbiTradeBeUpdated = new PrioritizedUbiTrade();
        currentSellPrioritizedUbiTradeBeUpdated.setTradeId("tradeId2");
        PrioritizedUbiTrade currentSellPrioritizedUbiTradeBeLeft = new PrioritizedUbiTrade();
        currentSellPrioritizedUbiTradeBeLeft.setTradeId("tradeId3");
        PrioritizedUbiTrade currentSellPrioritizedUbiTradeBeCanceled = new PrioritizedUbiTrade();
        currentSellPrioritizedUbiTradeBeCanceled.setTradeId("tradeId4");
        currentSellPrioritizedUbiTradeBeCanceled.setItem(new Item("44"));
        currentSellPrioritizedUbiTradeBeCanceled.getItem().setName("name44");
        currentSellPrioritizedUbiTradeBeCanceled.setProposedPaymentPrice(44);
        Collection<PrioritizedUbiTrade> currentSellPrioritizedUbiTrades = List.of(currentSellPrioritizedUbiTradeBeUpdated, currentSellPrioritizedUbiTradeBeLeft, currentSellPrioritizedUbiTradeBeCanceled);

        PersonalItem personalItemBuyTradeCreate = new PersonalItem();
        personalItemBuyTradeCreate.setItem(new Item("4"));
        personalItemBuyTradeCreate.getItem().setName("name4");
        personalItemBuyTradeCreate.setTradeAlreadyExists(false);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsBuyTradeCreate = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsBuyTradeCreate.setPrice(4);
        PotentialPersonalBuyTrade potentialBuyTradeCreate = new PotentialPersonalBuyTrade(personalItemBuyTradeCreate, prioritizedPotentialTradeStatsBuyTradeCreate);

        PersonalItem personalItemBuyTradeUpdate = new PersonalItem();
        personalItemBuyTradeUpdate.setItem(new Item("5"));
        personalItemBuyTradeUpdate.getItem().setName("name5");
        personalItemBuyTradeUpdate.setTradeAlreadyExists(true);
        personalItemBuyTradeUpdate.setExistingTrade(new UbiTrade());
        personalItemBuyTradeUpdate.getExistingTrade().setTradeId("tradeId5");
        personalItemBuyTradeUpdate.getExistingTrade().setProposedPaymentPrice(5);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsBuyTradeUpdate = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsBuyTradeUpdate.setPrice(50);
        PotentialPersonalBuyTrade potentialBuyTradeUpdate = new PotentialPersonalBuyTrade(personalItemBuyTradeUpdate, prioritizedPotentialTradeStatsBuyTradeUpdate);

        PersonalItem personalItemBuyTradeLeave = new PersonalItem();
        personalItemBuyTradeLeave.setItem(new Item("6"));
        personalItemBuyTradeLeave.getItem().setName("name6");
        personalItemBuyTradeLeave.setTradeAlreadyExists(true);
        personalItemBuyTradeLeave.setExistingTrade(new UbiTrade());
        personalItemBuyTradeLeave.getExistingTrade().setTradeId("tradeId6");
        personalItemBuyTradeLeave.getExistingTrade().setProposedPaymentPrice(6);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsBuyTradeLeave = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsBuyTradeLeave.setPrice(6);
        PotentialPersonalBuyTrade potentialBuyTradeLeave = new PotentialPersonalBuyTrade(personalItemBuyTradeLeave, prioritizedPotentialTradeStatsBuyTradeLeave);

        Collection<PotentialPersonalBuyTrade> resultingBuyTrades = List.of(potentialBuyTradeCreate, potentialBuyTradeUpdate, potentialBuyTradeLeave);

        PrioritizedUbiTrade currentBuyPrioritizedUbiTradeBeUpdated = new PrioritizedUbiTrade();
        currentBuyPrioritizedUbiTradeBeUpdated.setTradeId("tradeId5");
        PrioritizedUbiTrade currentBuyPrioritizedUbiTradeBeLeft = new PrioritizedUbiTrade();
        currentBuyPrioritizedUbiTradeBeLeft.setTradeId("tradeId6");
        PrioritizedUbiTrade currentBuyPrioritizedUbiTradeBeCanceled = new PrioritizedUbiTrade();
        currentBuyPrioritizedUbiTradeBeCanceled.setTradeId("tradeId7");
        currentBuyPrioritizedUbiTradeBeCanceled.setItem(new Item("77"));
        currentBuyPrioritizedUbiTradeBeCanceled.getItem().setName("name77");
        currentBuyPrioritizedUbiTradeBeCanceled.setProposedPaymentPrice(77);
        Collection<PrioritizedUbiTrade> currentBuyPrioritizedUbiTrades = List.of(currentBuyPrioritizedUbiTradeBeUpdated, currentBuyPrioritizedUbiTradeBeLeft, currentBuyPrioritizedUbiTradeBeCanceled);


        TradeManagerCommand commandCreateSell = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.SELL_ORDER_CREATE, potentialSellTradeCreate.getItemId(), potentialSellTradeCreate.getItemName(),
                potentialSellTradeCreate.getNewPrice());

        TradeManagerCommand commandUpdateSell = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.SELL_ORDER_UPDATE, potentialSellTradeUpdate.getItemId(), potentialSellTradeUpdate.getItemName(),
                potentialSellTradeUpdate.getTradeId(), potentialSellTradeUpdate.getOldPrice(), potentialSellTradeUpdate.getNewPrice());

        TradeManagerCommand commandCreateBuy = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.BUY_ORDER_CREATE, potentialBuyTradeCreate.getItemId(), potentialBuyTradeCreate.getItemName(),
                potentialBuyTradeCreate.getNewPrice());

        TradeManagerCommand commandUpdateBuy = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.BUY_ORDER_UPDATE, potentialBuyTradeUpdate.getItemId(), potentialBuyTradeUpdate.getItemName(),
                potentialBuyTradeUpdate.getTradeId(), potentialBuyTradeUpdate.getOldPrice(), potentialBuyTradeUpdate.getNewPrice());

        TradeManagerCommand commandCancelBuy = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.BUY_ORDER_CANCEL, currentBuyPrioritizedUbiTradeBeCanceled.getItemId(), currentBuyPrioritizedUbiTradeBeCanceled.getItemName(),
                currentBuyPrioritizedUbiTradeBeCanceled.getTradeId(), currentBuyPrioritizedUbiTradeBeCanceled.getProposedPaymentPrice());

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setSellSlots(4);
        configTrades.setBuySlots(3);

        List<TradeManagerCommand> result = tradeManagerCommandsFactory.createTradeManagerCommandsForUser(resultingSellTrades
                , currentSellPrioritizedUbiTrades, resultingBuyTrades, currentBuyPrioritizedUbiTrades, userId, authorizationDTO, configTrades);


        assertEquals(5, result.size());
        assertTrue(result.contains(commandCreateSell));
        assertTrue(result.contains(commandUpdateSell));
        assertTrue(result.contains(commandCreateBuy));
        assertTrue(result.contains(commandUpdateBuy));
        assertTrue(result.contains(commandCancelBuy));
    }

    @Test
    public void createCommandsForCentralTradeManagersForUser_should_return_expected_list_of_commands_if_Sell_trades_limit_exceeded() {
        Long userId = 1L;
        AuthorizationDTO authorizationDTO = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "rememberDeviceTicket", "rememberMeTicket");

        PersonalItem personalItemSellTradeCreate = new PersonalItem();
        personalItemSellTradeCreate.setItem(new Item("1"));
        personalItemSellTradeCreate.getItem().setName("name");
        personalItemSellTradeCreate.setTradeAlreadyExists(false);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsSellTradeCreate = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsSellTradeCreate.setPrice(1);
        PotentialPersonalSellTrade potentialSellTradeCreate = new PotentialPersonalSellTrade(personalItemSellTradeCreate, prioritizedPotentialTradeStatsSellTradeCreate);

        PersonalItem personalItemSellTradeUpdate = new PersonalItem();
        personalItemSellTradeUpdate.setItem(new Item("2"));
        personalItemSellTradeUpdate.getItem().setName("name2");
        personalItemSellTradeUpdate.setTradeAlreadyExists(true);
        personalItemSellTradeUpdate.setExistingTrade(new UbiTrade());
        personalItemSellTradeUpdate.getExistingTrade().setTradeId("tradeId2");
        personalItemSellTradeUpdate.getExistingTrade().setProposedPaymentPrice(2);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsSellTradeUpdate = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsSellTradeUpdate.setPrice(20);
        PotentialPersonalSellTrade potentialSellTradeUpdate = new PotentialPersonalSellTrade(personalItemSellTradeUpdate, prioritizedPotentialTradeStatsSellTradeUpdate);

        PersonalItem personalItemSellTradeLeave = new PersonalItem();
        personalItemSellTradeLeave.setItem(new Item("3"));
        personalItemSellTradeLeave.getItem().setName("name3");
        personalItemSellTradeLeave.setTradeAlreadyExists(true);
        personalItemSellTradeLeave.setExistingTrade(new UbiTrade());
        personalItemSellTradeLeave.getExistingTrade().setTradeId("tradeId3");
        personalItemSellTradeLeave.getExistingTrade().setProposedPaymentPrice(3);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsSellTradeLeave = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsSellTradeLeave.setPrice(3);
        PotentialPersonalSellTrade potentialSellTradeLeave = new PotentialPersonalSellTrade(personalItemSellTradeLeave, prioritizedPotentialTradeStatsSellTradeLeave);

        Collection<PotentialPersonalSellTrade> resultingSellTrades = List.of(potentialSellTradeCreate, potentialSellTradeUpdate, potentialSellTradeLeave);

        PrioritizedUbiTrade currentSellPrioritizedUbiTradeBeUpdated = new PrioritizedUbiTrade();
        currentSellPrioritizedUbiTradeBeUpdated.setTradeId("tradeId2");
        PrioritizedUbiTrade currentSellPrioritizedUbiTradeBeLeft = new PrioritizedUbiTrade();
        currentSellPrioritizedUbiTradeBeLeft.setTradeId("tradeId3");
        PrioritizedUbiTrade currentSellPrioritizedUbiTradeBeCanceled = new PrioritizedUbiTrade();
        currentSellPrioritizedUbiTradeBeCanceled.setTradeId("tradeId4");
        currentSellPrioritizedUbiTradeBeCanceled.setItem(new Item("44"));
        currentSellPrioritizedUbiTradeBeCanceled.getItem().setName("name44");
        currentSellPrioritizedUbiTradeBeCanceled.setProposedPaymentPrice(44);
        Collection<PrioritizedUbiTrade> currentSellPrioritizedUbiTrades = List.of(currentSellPrioritizedUbiTradeBeUpdated, currentSellPrioritizedUbiTradeBeLeft, currentSellPrioritizedUbiTradeBeCanceled);

        PersonalItem personalItemBuyTradeCreate = new PersonalItem();
        personalItemBuyTradeCreate.setItem(new Item("4"));
        personalItemBuyTradeCreate.getItem().setName("name4");
        personalItemBuyTradeCreate.setTradeAlreadyExists(false);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsBuyTradeCreate = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsBuyTradeCreate.setPrice(4);
        PotentialPersonalBuyTrade potentialBuyTradeCreate = new PotentialPersonalBuyTrade(personalItemBuyTradeCreate, prioritizedPotentialTradeStatsBuyTradeCreate);

        PersonalItem personalItemBuyTradeUpdate = new PersonalItem();
        personalItemBuyTradeUpdate.setItem(new Item("5"));
        personalItemBuyTradeUpdate.getItem().setName("name5");
        personalItemBuyTradeUpdate.setTradeAlreadyExists(true);
        personalItemBuyTradeUpdate.setExistingTrade(new UbiTrade());
        personalItemBuyTradeUpdate.getExistingTrade().setTradeId("tradeId5");
        personalItemBuyTradeUpdate.getExistingTrade().setProposedPaymentPrice(5);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsBuyTradeUpdate = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsBuyTradeUpdate.setPrice(50);
        PotentialPersonalBuyTrade potentialBuyTradeUpdate = new PotentialPersonalBuyTrade(personalItemBuyTradeUpdate, prioritizedPotentialTradeStatsBuyTradeUpdate);

        PersonalItem personalItemBuyTradeLeave = new PersonalItem();
        personalItemBuyTradeLeave.setItem(new Item("6"));
        personalItemBuyTradeLeave.getItem().setName("name6");
        personalItemBuyTradeLeave.setTradeAlreadyExists(true);
        personalItemBuyTradeLeave.setExistingTrade(new UbiTrade());
        personalItemBuyTradeLeave.getExistingTrade().setTradeId("tradeId6");
        personalItemBuyTradeLeave.getExistingTrade().setProposedPaymentPrice(6);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsBuyTradeLeave = new PrioritizedPotentialTradeStats();
        prioritizedPotentialTradeStatsBuyTradeLeave.setPrice(6);
        PotentialPersonalBuyTrade potentialBuyTradeLeave = new PotentialPersonalBuyTrade(personalItemBuyTradeLeave, prioritizedPotentialTradeStatsBuyTradeLeave);

        Collection<PotentialPersonalBuyTrade> resultingBuyTrades = List.of(potentialBuyTradeCreate, potentialBuyTradeUpdate, potentialBuyTradeLeave);

        PrioritizedUbiTrade currentBuyPrioritizedUbiTradeBeUpdated = new PrioritizedUbiTrade();
        currentBuyPrioritizedUbiTradeBeUpdated.setTradeId("tradeId5");
        PrioritizedUbiTrade currentBuyPrioritizedUbiTradeBeLeft = new PrioritizedUbiTrade();
        currentBuyPrioritizedUbiTradeBeLeft.setTradeId("tradeId6");
        PrioritizedUbiTrade currentBuyPrioritizedUbiTradeBeCanceled = new PrioritizedUbiTrade();
        currentBuyPrioritizedUbiTradeBeCanceled.setTradeId("tradeId7");
        currentBuyPrioritizedUbiTradeBeCanceled.setItem(new Item("77"));
        currentBuyPrioritizedUbiTradeBeCanceled.getItem().setName("name77");
        currentBuyPrioritizedUbiTradeBeCanceled.setProposedPaymentPrice(77);
        Collection<PrioritizedUbiTrade> currentBuyPrioritizedUbiTrades = List.of(currentBuyPrioritizedUbiTradeBeUpdated, currentBuyPrioritizedUbiTradeBeLeft, currentBuyPrioritizedUbiTradeBeCanceled);


        TradeManagerCommand commandCreateSell = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.SELL_ORDER_CREATE, potentialSellTradeCreate.getItemId(), potentialSellTradeCreate.getItemName(),
                potentialSellTradeCreate.getNewPrice());

        TradeManagerCommand commandUpdateSell = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.SELL_ORDER_UPDATE, potentialSellTradeUpdate.getItemId(), potentialSellTradeUpdate.getItemName(),
                potentialSellTradeUpdate.getTradeId(), potentialSellTradeUpdate.getOldPrice(), potentialSellTradeUpdate.getNewPrice());

        TradeManagerCommand commandCancelSell = new TradeManagerCommand(userId, authorizationDTO, CentralTradeManagerCommandType.SELL_ORDER_CANCEL, currentSellPrioritizedUbiTradeBeCanceled.getItemId(),
                currentSellPrioritizedUbiTradeBeCanceled.getItemName(), currentSellPrioritizedUbiTradeBeCanceled.getTradeId(), currentSellPrioritizedUbiTradeBeCanceled.getProposedPaymentPrice());

        TradeManagerCommand commandCreateBuy = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.BUY_ORDER_CREATE, potentialBuyTradeCreate.getItemId(), potentialBuyTradeCreate.getItemName(),
                potentialBuyTradeCreate.getNewPrice());

        TradeManagerCommand commandUpdateBuy = new TradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.BUY_ORDER_UPDATE, potentialBuyTradeUpdate.getItemId(), potentialBuyTradeUpdate.getItemName(),
                potentialBuyTradeUpdate.getTradeId(), potentialBuyTradeUpdate.getOldPrice(), potentialBuyTradeUpdate.getNewPrice());

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setSellSlots(3);
        configTrades.setBuySlots(4);

        List<TradeManagerCommand> result = tradeManagerCommandsFactory.createTradeManagerCommandsForUser(resultingSellTrades
                , currentSellPrioritizedUbiTrades, resultingBuyTrades, currentBuyPrioritizedUbiTrades, userId, authorizationDTO, configTrades);


        assertEquals(5, result.size());
        assertTrue(result.contains(commandCreateSell));
        assertTrue(result.contains(commandUpdateSell));
        assertTrue(result.contains(commandCancelSell));
        assertTrue(result.contains(commandCreateBuy));
        assertTrue(result.contains(commandUpdateBuy));
    }
}