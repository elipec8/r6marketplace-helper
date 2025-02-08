package github.ricemonger.trades_manager.services;

import github.ricemonger.marketplace.graphQl.personal_mutation_buy_create.PersonalMutationBuyCreateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_buy_update.PersonalMutationBuyUpdateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_cancel.PersonalMutationCancelGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_create.PersonalMutationSellCreateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_update.PersonalMutationSellUpdateGraphQlClientService;
import github.ricemonger.trades_manager.services.DTOs.TradeManagerCommand;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PrioritizedUbiTradeManagementCommandsExecutorTest {
    @Autowired
    private TradeManagementCommandsExecutor tradeManagementCommandsExecutor;
    @MockBean
    private PersonalMutationBuyCreateGraphQlClientService personalMutationBuyCreateGraphQlClientService;
    @MockBean
    private PersonalMutationBuyUpdateGraphQlClientService personalMutationBuyUpdateGraphQlClientService;
    @MockBean
    private PersonalMutationSellCreateGraphQlClientService personalMutationSellCreateGraphQlClientService;
    @MockBean
    private PersonalMutationSellUpdateGraphQlClientService personalMutationSellUpdateGraphQlClientService;
    @MockBean
    private PersonalMutationCancelGraphQlClientService personalMutationCancelGraphQlClientService;
    @MockBean
    private NotificationService notificationService;

    @Test
    public void executeCommand_should_execute_buy_cancel_command() {
        AuthorizationDTO authDTO1 = new AuthorizationDTO("ticket1", "profileId1", "spaceId1", "sessionId1", "rememberDeviceTicket1", "rememberMeTicket1");
        TradeManagerCommand buyCancelCommand1 = Mockito.mock(TradeManagerCommand.class);
        when(buyCancelCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_CANCEL);
        when(buyCancelCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyCancelCommand1.getTradeId()).thenReturn("buyCancelTradeId1");
        when(buyCancelCommand1.getUserId()).thenReturn(1L);

        tradeManagementCommandsExecutor.executeCommand(buyCancelCommand1);

        verify(personalMutationCancelGraphQlClientService).cancelOrderForUser(same(authDTO1), eq("buyCancelTradeId1"));
        verify(notificationService).sendTradeManagerNotification(eq(1L), anyString());
    }

    @Test
    public void executeCommand_should_execute_buy_update_command() {
        AuthorizationDTO authDTO1 = new AuthorizationDTO("ticket1", "profileId1", "spaceId1", "sessionId1", "rememberDeviceTicket1", "rememberMeTicket1");

        TradeManagerCommand buyUpdateCommand1 = Mockito.mock(TradeManagerCommand.class);
        when(buyUpdateCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_UPDATE);
        when(buyUpdateCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyUpdateCommand1.getTradeId()).thenReturn("buyUpdateTradeId1");
        when(buyUpdateCommand1.getNewPrice()).thenReturn(1);
        when(buyUpdateCommand1.getUserId()).thenReturn(1L);

        tradeManagementCommandsExecutor.executeCommand(buyUpdateCommand1);

        verify(personalMutationBuyUpdateGraphQlClientService).updateBuyOrderForUser(same(authDTO1), eq("buyUpdateTradeId1"), eq(1));
        verify(notificationService).sendTradeManagerNotification(eq(1L), anyString());
    }

    @Test
    public void executeCommand_should_execute_buy_create_command() {
        AuthorizationDTO authDTO1 = new AuthorizationDTO("ticket1", "profileId1", "spaceId1", "sessionId1", "rememberDeviceTicket1", "rememberMeTicket1");

        TradeManagerCommand buyCreateCommand1 = Mockito.mock(TradeManagerCommand.class);
        when(buyCreateCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.BUY_ORDER_CREATE);
        when(buyCreateCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(buyCreateCommand1.getItemId()).thenReturn("buyCreateItemId1");
        when(buyCreateCommand1.getNewPrice()).thenReturn(1);
        when(buyCreateCommand1.getUserId()).thenReturn(1L);

        tradeManagementCommandsExecutor.executeCommand(buyCreateCommand1);

        verify(personalMutationBuyCreateGraphQlClientService).createBuyOrderForUser(same(authDTO1), eq("buyCreateItemId1"), eq(1));
        verify(notificationService).sendTradeManagerNotification(eq(1L), anyString());
    }

    @Test
    public void executeCommand_should_execute_sell_cancel_command() {
        AuthorizationDTO authDTO1 = new AuthorizationDTO("ticket1", "profileId1", "spaceId1", "sessionId1", "rememberDeviceTicket1", "rememberMeTicket1");

        TradeManagerCommand sellCancelCommand1 = Mockito.mock(TradeManagerCommand.class);
        when(sellCancelCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_CANCEL);
        when(sellCancelCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellCancelCommand1.getTradeId()).thenReturn("sellCancelTradeId1");
        when(sellCancelCommand1.getUserId()).thenReturn(1L);

        tradeManagementCommandsExecutor.executeCommand(sellCancelCommand1);

        verify(personalMutationCancelGraphQlClientService).cancelOrderForUser(same(authDTO1), eq("sellCancelTradeId1"));
        verify(notificationService).sendTradeManagerNotification(eq(1L), anyString());
    }

    @Test
    public void executeCommand_should_execute_sell_update_command() {
        AuthorizationDTO authDTO1 = new AuthorizationDTO("ticket1", "profileId1", "spaceId1", "sessionId1", "rememberDeviceTicket1", "rememberMeTicket1");

        TradeManagerCommand sellUpdateCommand1 = Mockito.mock(TradeManagerCommand.class);
        when(sellUpdateCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_UPDATE);
        when(sellUpdateCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellUpdateCommand1.getTradeId()).thenReturn("sellUpdateTradeId1");
        when(sellUpdateCommand1.getNewPrice()).thenReturn(1);
        when(sellUpdateCommand1.getUserId()).thenReturn(1L);

        tradeManagementCommandsExecutor.executeCommand(sellUpdateCommand1);

        verify(personalMutationSellUpdateGraphQlClientService).updateSellOrderForUser(same(authDTO1), eq("sellUpdateTradeId1"), eq(1));
        verify(notificationService).sendTradeManagerNotification(eq(1L), anyString());
    }

    @Test
    public void executeCommand_should_execute_sell_create_command() {
        AuthorizationDTO authDTO1 = new AuthorizationDTO("ticket1", "profileId1", "spaceId1", "sessionId1", "rememberDeviceTicket1", "rememberMeTicket1");

        TradeManagerCommand sellCreateCommand1 = Mockito.mock(TradeManagerCommand.class);
        when(sellCreateCommand1.getCommandType()).thenReturn(CentralTradeManagerCommandType.SELL_ORDER_CREATE);
        when(sellCreateCommand1.getAuthorizationDTO()).thenReturn(authDTO1);
        when(sellCreateCommand1.getItemId()).thenReturn("sellCreateItemId1");
        when(sellCreateCommand1.getNewPrice()).thenReturn(1);
        when(sellCreateCommand1.getUserId()).thenReturn(1L);

        tradeManagementCommandsExecutor.executeCommand(sellCreateCommand1);

        verify(personalMutationSellCreateGraphQlClientService).createSellOrderForUser(same(authDTO1), eq("sellCreateItemId1"), eq(1));
        verify(notificationService).sendTradeManagerNotification(eq(1L), anyString());
    }
}