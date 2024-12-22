package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.factories.CentralTradeManagerCommandFactory;
import github.ricemonger.marketplace.services.factories.PersonalItemFactory;
import github.ricemonger.marketplace.services.factories.PotentialTradeFactory;
import github.ricemonger.telegramBot.TelegramBotService;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.CentralTradeManagerCommand;
import github.ricemonger.utils.DTOs.personal.ManageableUser;
import github.ricemonger.utils.DTOs.personal.UbiAccountStats;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CentralTradeManagerTest {
    @Autowired
    private CentralTradeManager centralTradeManager;
    @MockBean
    private GraphQlClientService graphQlClientService;
    @MockBean
    private TelegramBotService telegramBotService;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private ItemService itemService;
    @MockBean
    private UserService userService;
    @MockBean
    private PersonalItemFactory personalItemFactory;
    @MockBean
    private PotentialTradeFactory potentialTradeFactory;
    @MockBean
    private CentralTradeManagerCommandFactory centralTradeManagerCommandFactory;

    @Test
    public void manageAllUsersTrades_should_create_and_execute_commands_for_all_manageable_users_and_notify_notifiable_users() {
        List currentSellTrades1 = Mockito.mock(List.class);
        List currentBuyTrades1 = Mockito.mock(List.class);
        List ownedItemsIds1 = Mockito.mock(List.class);
        List tradeByFiltersManagers1 = Mockito.mock(List.class);
        List tradeByItemIdManagers1 = Mockito.mock(List.class);
        List resaleLocks1 = Mockito.mock(List.class);
        Integer creditAmount1 = 1000;
        Integer soldIn24h1 = 500;
        Integer boughtIn24h1 = 200;

        List currentSellTrades2 = Mockito.mock(List.class);
        List currentBuyTrades2 = Mockito.mock(List.class);
        List ownedItemsIds2 = Mockito.mock(List.class);
        List tradeByFiltersManagers2 = Mockito.mock(List.class);
        List tradeByItemIdManagers2 = Mockito.mock(List.class);
        List resaleLocks2 = Mockito.mock(List.class);
        Integer creditAmount2 = 10000;
        Integer soldIn24h2 = 5000;
        Integer boughtIn24h2 = 2000;

        UbiAccountStats validUbiAccountStats1 = new UbiAccountStats();
        validUbiAccountStats1.setUbiProfileId("profileId1");
        validUbiAccountStats1.setCreditAmount(creditAmount1);
        validUbiAccountStats1.setSoldIn24h(soldIn24h1);
        validUbiAccountStats1.setBoughtIn24h(boughtIn24h1);
        validUbiAccountStats1.setResaleLocks(resaleLocks1);
        validUbiAccountStats1.setCurrentSellTrades(currentSellTrades1);
        validUbiAccountStats1.setCurrentBuyTrades(currentBuyTrades1);
        validUbiAccountStats1.setOwnedItemsIds(ownedItemsIds1);

        UbiAccountStats validUbiAccountStats2 = new UbiAccountStats();
        validUbiAccountStats2.setUbiProfileId("profileId2");
        validUbiAccountStats2.setCreditAmount(creditAmount2);
        validUbiAccountStats2.setSoldIn24h(soldIn24h2);
        validUbiAccountStats2.setBoughtIn24h(boughtIn24h2);
        validUbiAccountStats2.setResaleLocks(resaleLocks2);
        validUbiAccountStats2.setCurrentSellTrades(currentSellTrades2);
        validUbiAccountStats2.setCurrentBuyTrades(currentBuyTrades2);
        validUbiAccountStats2.setOwnedItemsIds(ownedItemsIds2);

        UbiAccountStats nullManageableUserUbiAccount = new UbiAccountStats();
        nullManageableUserUbiAccount.setUbiProfileId("3");

        UbiAccountStats nullUbiAccountStats = null;

        List<UbiAccountStats> updateUbiAccount = new ArrayList<>();
        updateUbiAccount.add(validUbiAccountStats1);
        updateUbiAccount.add(validUbiAccountStats2);
        updateUbiAccount.add(nullManageableUserUbiAccount);
        updateUbiAccount.add(nullUbiAccountStats);

        ConfigTrades configTrades = Mockito.mock(ConfigTrades.class);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        List items = Mockito.mock(List.class);
        when(itemService.getAllItems()).thenReturn(items);

        ManageableUser validManageableUserTrueNotifications1 = new ManageableUser();
        validManageableUserTrueNotifications1.setId(1L);
        AuthorizationDTO authDTO1 = new AuthorizationDTO("ticket1", "profileId1", "spaceId1", "sessionId1", "rememberDeviceTicket1", "rememberMeTicket1");
        validManageableUserTrueNotifications1.setUbiAuthTicket("ticket1");
        validManageableUserTrueNotifications1.setUbiSpaceId("spaceId1");
        validManageableUserTrueNotifications1.setUbiSessionId("sessionId1");
        validManageableUserTrueNotifications1.setUbiRememberDeviceTicket("rememberDeviceTicket1");
        validManageableUserTrueNotifications1.setUbiRememberMeTicket("rememberMeTicket1");
        validManageableUserTrueNotifications1.setChatId("chatId1");
        validManageableUserTrueNotifications1.setPrivateNotificationsEnabledFlag(true);
        validManageableUserTrueNotifications1.setUbiAccountStatsEntityDTO(new UbiAccountStatsEntityDTO());
        validManageableUserTrueNotifications1.getUbiAccountStatsEntityDTO().setUbiProfileId("1");
        validManageableUserTrueNotifications1.getUbiAccountStatsEntityDTO().setCreditAmount(Mockito.mock(Integer.class));
        validManageableUserTrueNotifications1.setTradeByFiltersManagers(tradeByFiltersManagers1);
        validManageableUserTrueNotifications1.setTradeByItemIdManagers(tradeByItemIdManagers1);

        ManageableUser validManageableUserFalseNotifications2 = new ManageableUser();
        validManageableUserFalseNotifications2.setId(2L);
        AuthorizationDTO authDTO2 = new AuthorizationDTO("ticket2", "profileId2", "spaceId2", "sessionId2", "rememberDeviceTicket2", "rememberMeTicket2");
        validManageableUserFalseNotifications2.setUbiAuthTicket("ticket2");
        validManageableUserFalseNotifications2.setUbiSpaceId("spaceId2");
        validManageableUserFalseNotifications2.setUbiSessionId("sessionId2");
        validManageableUserFalseNotifications2.setUbiRememberDeviceTicket("rememberDeviceTicket2");
        validManageableUserFalseNotifications2.setUbiRememberMeTicket("rememberMeTicket2");
        validManageableUserFalseNotifications2.setChatId("chatId2");
        validManageableUserFalseNotifications2.setPrivateNotificationsEnabledFlag(false);
        validManageableUserFalseNotifications2.setUbiAccountStatsEntityDTO(new UbiAccountStatsEntityDTO());
        validManageableUserFalseNotifications2.getUbiAccountStatsEntityDTO().setUbiProfileId("2");
        validManageableUserFalseNotifications2.getUbiAccountStatsEntityDTO().setCreditAmount(Mockito.mock(Integer.class));
        validManageableUserFalseNotifications2.setTradeByFiltersManagers(tradeByFiltersManagers2);
        validManageableUserFalseNotifications2.setTradeByItemIdManagers(tradeByItemIdManagers2);

        ManageableUser nullManageableUser = null;

        ManageableUser nullLinkedUbiAccountUser = new ManageableUser();
        nullLinkedUbiAccountUser.setUbiAccountStatsEntityDTO(new UbiAccountStatsEntityDTO());
        nullLinkedUbiAccountUser.getUbiAccountStatsEntityDTO().setUbiProfileId("4");

        List<ManageableUser> manageableUsers = new ArrayList<>();
        manageableUsers.add(validManageableUserTrueNotifications1);
        manageableUsers.add(validManageableUserFalseNotifications2);
        manageableUsers.add(nullManageableUser);
        manageableUsers.add(nullLinkedUbiAccountUser);

        when(userService.getAllManageableUsers()).thenReturn(manageableUsers);

        Set personalItems1 = Mockito.mock(Set.class);
        when(personalItemFactory.getPersonalItemsForUser(same(tradeByFiltersManagers1), same(tradeByItemIdManagers1), same(currentSellTrades1), same(currentBuyTrades1), same(ownedItemsIds1), same(items))).thenReturn(personalItems1);

        List resultingSellTrades1 = Mockito.mock(List.class);
        when(potentialTradeFactory.getResultingPersonalSellTrades(same(personalItems1), same(resaleLocks1), same(soldIn24h1), same(configTrades.getSellSlots()), same(configTrades.getSellLimit()))).thenReturn(resultingSellTrades1);

        List resultingBuyTrades1 = Mockito.mock(List.class);
        when(potentialTradeFactory.getResultingPersonalBuyTrades(same(personalItems1), same(creditAmount1), same(boughtIn24h2), same(configTrades.getBuySlots()), same(configTrades.getBuyLimit()))).thenReturn(resultingBuyTrades1);

        CentralTradeManagerCommand buyCancelCommand1 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyCancelCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_CANCEL);
        when(buyCancelCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyCancelCommand1.getTradeId()).thenReturn("buyCancelTradeId1");
        when(buyCancelCommand1.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand buyCancelCommand12 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyCancelCommand12.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_CANCEL);
        when(buyCancelCommand12.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyCancelCommand12.getTradeId()).thenReturn("buyCancelTradeId12");
        when(buyCancelCommand12.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand buyUpdateCommand1 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyUpdateCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_UPDATE);
        when(buyUpdateCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyUpdateCommand1.getTradeId()).thenReturn("buyUpdateTradeId1");
        when(buyUpdateCommand1.getNewPrice()).thenReturn(1);
        when(buyUpdateCommand1.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand buyUpdateCommand12 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyUpdateCommand12.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_UPDATE);
        when(buyUpdateCommand12.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyUpdateCommand12.getTradeId()).thenReturn("buyUpdateTradeId12");
        when(buyUpdateCommand12.getNewPrice()).thenReturn(1);
        when(buyUpdateCommand12.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand buyCreateCommand1 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyCreateCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_CREATE);
        when(buyCreateCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyCreateCommand1.getItemId()).thenReturn("buyCreateItemId1");
        when(buyCreateCommand1.getNewPrice()).thenReturn(1);
        when(buyCreateCommand1.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand buyCreateCommand12 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyCreateCommand12.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_CREATE);
        when(buyCreateCommand12.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyCreateCommand12.getItemId()).thenReturn("buyCreateItemId12");
        when(buyCreateCommand12.getNewPrice()).thenReturn(1);
        when(buyCreateCommand12.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand sellCancelCommand1 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellCancelCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_CANCEL);
        when(sellCancelCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellCancelCommand1.getTradeId()).thenReturn("sellCancelTradeId1");
        when(sellCancelCommand1.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand sellCancelCommand12 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellCancelCommand12.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_CANCEL);
        when(sellCancelCommand12.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellCancelCommand12.getTradeId()).thenReturn("sellCancelTradeId12");
        when(sellCancelCommand12.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand sellUpdateCommand1 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellUpdateCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_UPDATE);
        when(sellUpdateCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellUpdateCommand1.getTradeId()).thenReturn("sellUpdateTradeId1");
        when(sellUpdateCommand1.getNewPrice()).thenReturn(1);
        when(sellUpdateCommand1.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand sellUpdateCommand12 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellUpdateCommand12.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_UPDATE);
        when(sellUpdateCommand12.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellUpdateCommand12.getTradeId()).thenReturn("sellUpdateTradeId12");
        when(sellUpdateCommand12.getNewPrice()).thenReturn(1);
        when(sellUpdateCommand12.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand sellCreateCommand1 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellCreateCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_CREATE);
        when(sellCreateCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellCreateCommand1.getItemId()).thenReturn("sellCreateItemId1");
        when(sellCreateCommand1.getNewPrice()).thenReturn(1);
        when(sellCreateCommand1.getChatId()).thenReturn("chatId1");

        CentralTradeManagerCommand sellCreateCommand12 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellCreateCommand12.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_CREATE);
        when(sellCreateCommand12.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellCreateCommand12.getItemId()).thenReturn("sellCreateItemId12");
        when(sellCreateCommand12.getNewPrice()).thenReturn(1);
        when(sellCreateCommand12.getChatId()).thenReturn("chatId1");

        List<CentralTradeManagerCommand> commands1 = List.of(buyCancelCommand1,buyCancelCommand12,  buyUpdateCommand1,buyUpdateCommand12,
                buyCreateCommand1,buyCreateCommand12, sellCancelCommand1, sellCancelCommand12, sellUpdateCommand1, sellUpdateCommand12,
                sellCreateCommand1,sellCreateCommand12);
        when(centralTradeManagerCommandFactory.createCommandsForCentralTradeManagerForUser(same(resultingSellTrades1), same(currentSellTrades1), same(resultingBuyTrades1), same(currentBuyTrades1),eq(1L), eq(authDTO1), eq("chatId1"), eq(true))).thenReturn(commands1);

        Set personalItems2 = Mockito.mock(Set.class);
        when(personalItemFactory.getPersonalItemsForUser(same(tradeByFiltersManagers2), same(tradeByItemIdManagers2), same(currentSellTrades2), same(currentBuyTrades2), same(ownedItemsIds2), same(items))).thenReturn(personalItems2);

        List resultingSellTrades2 = Mockito.mock(List.class);
        when(potentialTradeFactory.getResultingPersonalSellTrades(same(personalItems2), same(resaleLocks2), same(soldIn24h2), same(configTrades.getSellSlots()), same(configTrades.getSellLimit()))).thenReturn(resultingSellTrades2);

        List resultingBuyTrades2 = Mockito.mock(List.class);
        when(potentialTradeFactory.getResultingPersonalBuyTrades(same(personalItems2), same(creditAmount1), same(boughtIn24h2), same(configTrades.getBuySlots()), same(configTrades.getBuyLimit()))).thenReturn(resultingBuyTrades2);

        CentralTradeManagerCommand buyCancelCommand2 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyCancelCommand2.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_CANCEL);
        when(buyCancelCommand2.getAuthorizationDTO()).thenReturn(authDTO2);
        when(buyCancelCommand2.getTradeId()).thenReturn("buyCancelTradeId2");
        when(buyCancelCommand2.getChatId()).thenReturn("chatId2");

        CentralTradeManagerCommand buyUpdateCommand2 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyUpdateCommand2.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_UPDATE);
        when(buyUpdateCommand2.getAuthorizationDTO()).thenReturn(authDTO2);
        when(buyUpdateCommand2.getTradeId()).thenReturn("buyUpdateTradeId2");
        when(buyUpdateCommand2.getNewPrice()).thenReturn(1);
        when(buyUpdateCommand2.getChatId()).thenReturn("chatId2");

        CentralTradeManagerCommand buyCreateCommand2 = Mockito.mock(CentralTradeManagerCommand.class);
        when(buyCreateCommand2.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_CREATE);
        when(buyCreateCommand2.getAuthorizationDTO()).thenReturn(authDTO2);
        when(buyCreateCommand2.getItemId()).thenReturn("buyCreateItemId2");
        when(buyCreateCommand2.getNewPrice()).thenReturn(1);
        when(buyCreateCommand2.getChatId()).thenReturn("chatId2");

        CentralTradeManagerCommand sellCancelCommand2 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellCancelCommand2.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_CANCEL);
        when(sellCancelCommand2.getAuthorizationDTO()).thenReturn(authDTO2);
        when(sellCancelCommand2.getTradeId()).thenReturn("sellCancelTradeId2");
        when(sellCancelCommand2.getChatId()).thenReturn("chatId2");

        CentralTradeManagerCommand sellUpdateCommand2 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellUpdateCommand2.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_UPDATE);
        when(sellUpdateCommand2.getAuthorizationDTO()).thenReturn(authDTO2);
        when(sellUpdateCommand2.getTradeId()).thenReturn("sellUpdateTradeId2");
        when(sellUpdateCommand2.getNewPrice()).thenReturn(1);
        when(sellUpdateCommand2.getChatId()).thenReturn("chatId2");

        CentralTradeManagerCommand sellCreateCommand2 = Mockito.mock(CentralTradeManagerCommand.class);
        when(sellCreateCommand2.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_CREATE);
        when(sellCreateCommand2.getAuthorizationDTO()).thenReturn(authDTO2);
        when(sellCreateCommand2.getItemId()).thenReturn("sellCreateItemId2");
        when(sellCreateCommand2.getNewPrice()).thenReturn(1);
        when(sellCreateCommand2.getChatId()).thenReturn("chatId2");

        List<CentralTradeManagerCommand> commands2 = List.of(buyCancelCommand2,  buyUpdateCommand2, buyCreateCommand2, sellCancelCommand2, sellUpdateCommand2, sellCreateCommand2);
        when(centralTradeManagerCommandFactory.createCommandsForCentralTradeManagerForUser(same(resultingSellTrades2), same(currentSellTrades2), same(resultingBuyTrades2), same(currentBuyTrades2),eq(2L), eq(authDTO2), eq("chatId2"), eq(false))).thenReturn(commands2);

        centralTradeManager.manageAllUsersTrades(updateUbiAccount);

        verify(graphQlClientService).cancelOrderForUser(authDTO1, "buyCancelTradeId1");
        verify(graphQlClientService).cancelOrderForUser(authDTO1, "buyCancelTradeId12");
        verify(graphQlClientService).updateBuyOrderForUser(authDTO1, "buyUpdateTradeId1", 1);
        verify(graphQlClientService).updateBuyOrderForUser(authDTO1, "buyUpdateTradeId12", 1);
        verify(graphQlClientService).createBuyOrderForUser(authDTO1, "buyCreateItemId1", 1);
        verify(graphQlClientService).createBuyOrderForUser(authDTO1, "buyCreateItemId12", 1);
        verify(graphQlClientService).cancelOrderForUser(authDTO1, "sellCancelTradeId1");
        verify(graphQlClientService).cancelOrderForUser(authDTO1, "sellCancelTradeId12");
        verify(graphQlClientService).updateSellOrderForUser(authDTO1, "sellUpdateTradeId1", 1);
        verify(graphQlClientService).updateSellOrderForUser(authDTO1, "sellUpdateTradeId12", 1);
        verify(graphQlClientService).createSellOrderForUser(authDTO1, "sellCreateItemId1", 1);
        verify(graphQlClientService).createSellOrderForUser(authDTO1, "sellCreateItemId12", 1);

        verify(graphQlClientService).cancelOrderForUser(authDTO2, "buyCancelTradeId2");
        verify(graphQlClientService).updateBuyOrderForUser(authDTO2, "buyUpdateTradeId2", 1);
        verify(graphQlClientService).createBuyOrderForUser(authDTO2, "buyCreateItemId2", 1);
        verify(graphQlClientService).cancelOrderForUser(authDTO2, "sellCancelTradeId2");
        verify(graphQlClientService).updateSellOrderForUser(authDTO2, "sellUpdateTradeId2", 1);
        verify(graphQlClientService).createSellOrderForUser(authDTO2, "sellCreateItemId2", 1);

        verify(telegramBotService,times(12)).sendNotificationToUser(eq("chatId1"), anyString());

        verify(telegramBotService,times(0)).sendNotificationToUser(eq("chatId2"), anyString());
    }
}