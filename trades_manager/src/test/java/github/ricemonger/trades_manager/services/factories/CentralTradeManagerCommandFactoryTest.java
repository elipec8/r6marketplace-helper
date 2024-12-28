package github.ricemonger.trades_manager.services.factories;

import github.ricemonger.trades_manager.services.DTOs.CentralTradeManagerCommand;
import github.ricemonger.trades_manager.services.DTOs.PersonalItem;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalBuyTrade;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalSellTrade;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CentralTradeManagerCommandFactoryTest {
    @Autowired
    private CentralTradeManagerCommandFactory centralTradeManagerCommandFactory;

    @Test
    public void createCommandsForCentralTradeManagersForUser_should_return_expected_list_of_commands() {
        Long userId = 1L;
        AuthorizationDTO authorizationDTO = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "rememberDeviceTicket", "rememberMeTicket");
        String chatId = "chatId";
        boolean privateNotificationsEnabledFlag = true;

        PersonalItem personalItemSellTradeCreate = new PersonalItem();
        personalItemSellTradeCreate.setItem(new Item("1"));
        personalItemSellTradeCreate.getItem().setName("name");
        personalItemSellTradeCreate.setTradeAlreadyExists(false);
        PotentialTradeStats potentialTradeStatsSellTradeCreate = new PotentialTradeStats();
        potentialTradeStatsSellTradeCreate.setPrice(1);
        PotentialPersonalSellTrade potentialSellTradeCreate = new PotentialPersonalSellTrade(personalItemSellTradeCreate, potentialTradeStatsSellTradeCreate);

        PersonalItem personalItemSellTradeUpdate = new PersonalItem();
        personalItemSellTradeUpdate.setItem(new Item("2"));
        personalItemSellTradeUpdate.getItem().setName("name2");
        personalItemSellTradeUpdate.setTradeAlreadyExists(true);
        personalItemSellTradeUpdate.setExistingTrade(new UbiTrade());
        personalItemSellTradeUpdate.getExistingTrade().setTradeId("tradeId2");
        personalItemSellTradeUpdate.getExistingTrade().setProposedPaymentPrice(2);
        PotentialTradeStats potentialTradeStatsSellTradeUpdate = new PotentialTradeStats();
        potentialTradeStatsSellTradeUpdate.setPrice(20);
        PotentialPersonalSellTrade potentialSellTradeUpdate = new PotentialPersonalSellTrade(personalItemSellTradeUpdate, potentialTradeStatsSellTradeUpdate);

        PersonalItem personalItemSellTradeLeave = new PersonalItem();
        personalItemSellTradeLeave.setItem(new Item("3"));
        personalItemSellTradeLeave.getItem().setName("name3");
        personalItemSellTradeLeave.setTradeAlreadyExists(true);
        personalItemSellTradeLeave.setExistingTrade(new UbiTrade());
        personalItemSellTradeLeave.getExistingTrade().setTradeId("tradeId3");
        personalItemSellTradeLeave.getExistingTrade().setProposedPaymentPrice(3);
        PotentialTradeStats potentialTradeStatsSellTradeLeave = new PotentialTradeStats();
        potentialTradeStatsSellTradeLeave.setPrice(3);
        PotentialPersonalSellTrade potentialSellTradeLeave = new PotentialPersonalSellTrade(personalItemSellTradeLeave, potentialTradeStatsSellTradeLeave);

        Collection<PotentialPersonalSellTrade> resultingSellTrades = List.of(potentialSellTradeCreate, potentialSellTradeUpdate, potentialSellTradeLeave);

        UbiTrade currentSellTradeBeUpdated = new UbiTrade();
        currentSellTradeBeUpdated.setTradeId("tradeId2");
        UbiTrade currentSellTradeBeLeft = new UbiTrade();
        currentSellTradeBeLeft.setTradeId("tradeId3");
        UbiTrade currentSellTradeBeCanceled = new UbiTrade();
        currentSellTradeBeCanceled.setTradeId("tradeId4");
        currentSellTradeBeCanceled.setItem(new Item("44"));
        currentSellTradeBeCanceled.getItem().setName("name44");
        currentSellTradeBeCanceled.setProposedPaymentPrice(44);
        Collection<UbiTrade> currentSellTrades = List.of(currentSellTradeBeUpdated, currentSellTradeBeLeft, currentSellTradeBeCanceled);

        PersonalItem personalItemBuyTradeCreate = new PersonalItem();
        personalItemBuyTradeCreate.setItem(new Item("4"));
        personalItemBuyTradeCreate.getItem().setName("name4");
        personalItemBuyTradeCreate.setTradeAlreadyExists(false);
        PotentialTradeStats potentialTradeStatsBuyTradeCreate = new PotentialTradeStats();
        potentialTradeStatsBuyTradeCreate.setPrice(4);
        PotentialPersonalBuyTrade potentialBuyTradeCreate = new PotentialPersonalBuyTrade(personalItemBuyTradeCreate, potentialTradeStatsBuyTradeCreate);

        PersonalItem personalItemBuyTradeUpdate = new PersonalItem();
        personalItemBuyTradeUpdate.setItem(new Item("5"));
        personalItemBuyTradeUpdate.getItem().setName("name5");
        personalItemBuyTradeUpdate.setTradeAlreadyExists(true);
        personalItemBuyTradeUpdate.setExistingTrade(new UbiTrade());
        personalItemBuyTradeUpdate.getExistingTrade().setTradeId("tradeId5");
        personalItemBuyTradeUpdate.getExistingTrade().setProposedPaymentPrice(5);
        PotentialTradeStats potentialTradeStatsBuyTradeUpdate = new PotentialTradeStats();
        potentialTradeStatsBuyTradeUpdate.setPrice(50);
        PotentialPersonalBuyTrade potentialBuyTradeUpdate = new PotentialPersonalBuyTrade(personalItemBuyTradeUpdate, potentialTradeStatsBuyTradeUpdate);

        PersonalItem personalItemBuyTradeLeave = new PersonalItem();
        personalItemBuyTradeLeave.setItem(new Item("6"));
        personalItemBuyTradeLeave.getItem().setName("name6");
        personalItemBuyTradeLeave.setTradeAlreadyExists(true);
        personalItemBuyTradeLeave.setExistingTrade(new UbiTrade());
        personalItemBuyTradeLeave.getExistingTrade().setTradeId("tradeId6");
        personalItemBuyTradeLeave.getExistingTrade().setProposedPaymentPrice(6);
        PotentialTradeStats potentialTradeStatsBuyTradeLeave = new PotentialTradeStats();
        potentialTradeStatsBuyTradeLeave.setPrice(6);
        PotentialPersonalBuyTrade potentialBuyTradeLeave = new PotentialPersonalBuyTrade(personalItemBuyTradeLeave, potentialTradeStatsBuyTradeLeave);

        Collection<PotentialPersonalBuyTrade> resultingBuyTrades = List.of(potentialBuyTradeCreate, potentialBuyTradeUpdate, potentialBuyTradeLeave);

        UbiTrade currentBuyTradeBeUpdated = new UbiTrade();
        currentBuyTradeBeUpdated.setTradeId("tradeId5");
        UbiTrade currentBuyTradeBeLeft = new UbiTrade();
        currentBuyTradeBeLeft.setTradeId("tradeId6");
        UbiTrade currentBuyTradeBeCanceled = new UbiTrade();
        currentBuyTradeBeCanceled.setTradeId("tradeId7");
        currentBuyTradeBeCanceled.setItem(new Item("77"));
        currentBuyTradeBeCanceled.getItem().setName("name77");
        currentBuyTradeBeCanceled.setProposedPaymentPrice(77);
        Collection<UbiTrade> currentBuyTrades = List.of(currentBuyTradeBeUpdated, currentBuyTradeBeLeft, currentBuyTradeBeCanceled);

        CentralTradeManagerCommand commandCreateSell = new CentralTradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.SELL_ORDER_CREATE, potentialSellTradeCreate.getItemId(), potentialSellTradeCreate.getItemName(),
                potentialSellTradeCreate.getNewPrice());

        CentralTradeManagerCommand commandUpdateSell = new CentralTradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.SELL_ORDER_UPDATE, potentialSellTradeUpdate.getItemId(), potentialSellTradeUpdate.getItemName(),
                potentialSellTradeUpdate.getTradeId(), potentialSellTradeUpdate.getOldPrice(), potentialSellTradeUpdate.getNewPrice());

        CentralTradeManagerCommand commandCancelSell = new CentralTradeManagerCommand(userId, authorizationDTO,  CentralTradeManagerCommandType.SELL_ORDER_CANCEL, currentSellTradeBeCanceled.getItemId(),
                currentSellTradeBeCanceled.getItemName(), currentSellTradeBeCanceled.getTradeId(), currentSellTradeBeCanceled.getProposedPaymentPrice());

        CentralTradeManagerCommand commandCreateBuy = new CentralTradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.BUY_ORDER_CREATE, potentialBuyTradeCreate.getItemId(), potentialBuyTradeCreate.getItemName(),
                potentialBuyTradeCreate.getNewPrice());

        CentralTradeManagerCommand commandUpdateBuy = new CentralTradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.BUY_ORDER_UPDATE, potentialBuyTradeUpdate.getItemId(), potentialBuyTradeUpdate.getItemName(),
                potentialBuyTradeUpdate.getTradeId(), potentialBuyTradeUpdate.getOldPrice(), potentialBuyTradeUpdate.getNewPrice());

        CentralTradeManagerCommand commandCancelBuy = new CentralTradeManagerCommand(userId, authorizationDTO,
                CentralTradeManagerCommandType.BUY_ORDER_CANCEL, currentBuyTradeBeCanceled.getItemId(), currentBuyTradeBeCanceled.getItemName(),
                currentBuyTradeBeCanceled.getTradeId(), currentBuyTradeBeCanceled.getProposedPaymentPrice());

        List<CentralTradeManagerCommand> result = centralTradeManagerCommandFactory.createCommandsForCentralTradeManagerForUser(resultingSellTrades, currentSellTrades, resultingBuyTrades, currentBuyTrades, userId, authorizationDTO);

        assertEquals(6, result.size());
        assertTrue(result.contains(commandCreateSell));
        assertTrue(result.contains(commandUpdateSell));
        assertTrue(result.contains(commandCancelSell));
        assertTrue(result.contains(commandCreateBuy));
        assertTrue(result.contains(commandUpdateBuy));
        assertTrue(result.contains(commandCancelBuy));
    }
}