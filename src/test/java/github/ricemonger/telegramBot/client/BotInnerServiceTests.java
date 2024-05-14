package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.databases.neo4j.enums.ItemType;
import github.ricemonger.marketplace.databases.neo4j.services.ItemService;
import github.ricemonger.marketplace.databases.neo4j.services.TelegramLinkedUserService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BotInnerServiceTests {

    @MockBean
    private TelegramBotClientService telegramBotClientService;

    @MockBean
    private TelegramLinkedUserService telegramLinkedUserService;

    @MockBean
    private ItemService itemService;

    @Autowired
    private BotInnerService botInnerService;

    @Test
    public void askFromInlineKeyboardShouldHandleToService() {
        UpdateInfo updateInfo = new UpdateInfo();
        String text = "text";
        int buttonsInLine = 1;
        CallbackButton[] buttons = new CallbackButton[1];

        botInnerService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);

        verify(telegramBotClientService).askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    @Test
    public void sendTextShouldHandleToService() {
        UpdateInfo updateInfo = new UpdateInfo();
        String answer = "answer";

        botInnerService.sendText(updateInfo, answer);

        verify(telegramBotClientService).sendText(updateInfo, answer);
    }

    @Test
    public void isRegisteredShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.isRegistered(chatId);

        verify(telegramLinkedUserService).isTelegramUserRegistered(chatId);
    }

    @Test
    public void isRegisteredShouldReturnServiceAnswerIfTrue() {
        Long chatId = 1L;

        when(telegramLinkedUserService.isTelegramUserRegistered(chatId)).thenReturn(true);

        assertTrue(botInnerService.isRegistered(chatId));

        when(telegramLinkedUserService.isTelegramUserRegistered(chatId)).thenReturn(true);

        assertTrue(botInnerService.isRegistered(chatId));
    }

    @Test
    public void registerUserShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.registerUser(chatId);

        verify(telegramLinkedUserService).registerTelegramUser(chatId);
    }

    @Test
    public void addCredentialsFromUserInputsShouldHandleToServiceAndAddIfFull() {
        Long chatId = 1L;
        when(telegramLinkedUserService.getUserInputByStateOrNull(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email:password");

        botInnerService.addCredentialsFromUserInputs(chatId);

        verify(telegramLinkedUserService).addCredentials(chatId, "email", "password");

        verify(telegramLinkedUserService).clearUserInputs(chatId);
    }

    @Test
    public void addCredentialsFromUserInputsShouldHandleToServiceAndAddIfSeparated() {
        Long chatId = 1L;
        when(telegramLinkedUserService.getUserInputByStateOrNull(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email");
        when(telegramLinkedUserService.getUserInputByStateOrNull(chatId, InputState.CREDENTIALS_PASSWORD)).thenReturn("password");

        botInnerService.addCredentialsFromUserInputs(chatId);

        verify(telegramLinkedUserService).addCredentials(chatId, "email", "password");

        verify(telegramLinkedUserService).clearUserInputs(chatId);
    }

    @Test
    public void saveUserInputOrThrowShouldHandleToService() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasMessage(true);
        updateInfo.setMessageText("userInput");

        botInnerService.saveUserInputOrThrow(updateInfo);

        verify(telegramLinkedUserService).saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), "userInput");
    }

    @Test
    public void clearUserInputsShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.clearUserInputs(chatId);

        verify(telegramLinkedUserService).clearUserInputs(chatId);
    }

    @Test
    public void setUserNextInputStateShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.setUserNextInputState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        verify(telegramLinkedUserService).setUserNextInputState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);
    }

    @Test
    public void setUserNextInputGroupShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.setUserNextInputGroup(chatId, InputGroup.CREDENTIALS_ADD);

        verify(telegramLinkedUserService).setUserNextInputGroup(chatId, InputGroup.CREDENTIALS_ADD);
    }

    @Test
    public void getUserInputByStateShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        verify(telegramLinkedUserService).getUserInputByStateOrNull(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);
    }

    @Test
    public void getUserInputByStateShouldReturnServiceAnswer() {
        Long chatId = 1L;

        when(telegramLinkedUserService.getUserInputByStateOrNull(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("userInput");

        assertEquals("userInput", botInnerService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void removeCredentialsByUserInputsShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.removeCredentialsByUserInputs(chatId);

        verify(telegramLinkedUserService).removeCredentialsByUserInputs(chatId);
    }

    @Test
    public void removeUserAllCredentialsShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.removeUserAllCredentials(chatId);

        verify(telegramLinkedUserService).removeAllCredentials(chatId);
    }

    @Test
    public void getCredentialsEmailsListShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.getCredentialsEmailsList(chatId);

        verify(telegramLinkedUserService).getCredentialsEmailsList(chatId);
    }

    @Test
    public void getCredentialsEmailsListShouldReturnServiceAnswer() {
        Long chatId = 1L;

        when(telegramLinkedUserService.getCredentialsEmailsList(chatId)).thenReturn(List.of("email1", "email2"));

        assertEquals(List.of("email1", "email2"), botInnerService.getCredentialsEmailsList(chatId));
    }

    @Test
    public void sendDefaultSpeculativeItemsAsMessagesShouldGetFromServiceWithDefaultValuesAndSendByItemsAmount() {
        ItemEntity itemEntity = ItemEntity.builder()
                .itemFullId("id")
                .assetUrl("url")
                .name("name")
                .tags(List.of("tag1", "tag2"))
                .type(ItemType.WeaponSkin)
                .maxBuyPrice(100)
                .buyOrders(200)
                .minSellPrice(50)
                .sellOrders(100)
                .lastSoldPrice(150)
                .lastSoldAt(new Date())
                .expectedProfit(15)
                .expectedProfitPercentage(12)
                .build();

        Long chatId = 1L;

        when(itemService.getSpeculativeItemsByExpectedProfit(50, 40, 0, 15000)).thenReturn(List.of(itemEntity, itemEntity));

        botInnerService.sendDefaultSpeculativeItemsAsMessages(chatId);

        verify(itemService).getSpeculativeItemsByExpectedProfit(50, 40, 0, 15000);

        verify(telegramBotClientService,times(2)).sendText(eq(String.valueOf(chatId)), anyString());
    }
}
