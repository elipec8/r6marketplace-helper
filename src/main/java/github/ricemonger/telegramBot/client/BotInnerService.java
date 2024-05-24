package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.databases.postgres.services.TelegramUserService;
import github.ricemonger.marketplace.databases.postgres.services.Item;
import github.ricemonger.marketplace.databases.postgres.services.ItemService;
import github.ricemonger.marketplace.databases.postgres.services.UbiUserService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotInnerService {

    private final TelegramBotClientService telegramBotClientService;

    private final TelegramUserService telegramUserService;

    private final ItemService itemService;

    private final UbiUserService ubiUserService;

    public void askFromInlineKeyboard(UpdateInfo updateInfo, String text, int buttonsInLine, CallbackButton[] buttons) {
        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    public void sendText(UpdateInfo updateInfo, String answer) {
        telegramBotClientService.sendText(updateInfo, answer);
    }

    public boolean isRegistered(Long chatId) {
        return telegramUserService.isTelegramUserRegistered(chatId);
    }

    public void registerUser(Long chatId) {
        telegramUserService.registerTelegramUser(chatId);
    }

    public void addCredentialsFromUserInputs(Long chatId) {
        String fullOrEmail = getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        if (fullOrEmail.contains(":")) {
            String email = fullOrEmail.substring(0, fullOrEmail.indexOf(":"));
            String password = fullOrEmail.substring(fullOrEmail.indexOf(":") + 1);
            clearUserInputs(chatId);
            telegramUserService.addCredentialsIfValidOrThrowException(chatId, email, password);
        } else {
            String password = getUserInputByState(chatId, InputState.CREDENTIALS_PASSWORD);
            clearUserInputs(chatId);
            telegramUserService.addCredentialsIfValidOrThrowException(chatId, fullOrEmail, password);
        }

    }

    public void removeCredentialsByUserInputs(Long chatId) {
        telegramUserService.removeCredentialsByUserInputs(chatId);

        clearUserInputs(chatId);
    }

    public void saveUserInputOrThrow(UpdateInfo updateInfo) {
        String userInput;

        if (updateInfo.isHasMessage()) {
            userInput = updateInfo.getMessageText();
        } else if (updateInfo.isHasCallBackQuery()) {
            userInput = updateInfo.getCallbackQueryData();
        } else {
            throw new IllegalStateException("UpdateInfo has no message or callback query");
        }
        telegramUserService.saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), userInput);
    }

    public void clearUserInputs(Long chatId) {
        telegramUserService.clearUserInputs(chatId);
    }

    public void setUserNextInputState(Long chatId, InputState inputState) {
        telegramUserService.setUserNextInputState(chatId, inputState);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) {
        telegramUserService.setUserNextInputGroup(chatId, inputGroup);
    }

    public String getUserInputByState(Long chatId, InputState inputState) {
        return telegramUserService.getUserInputByStateOrNull(chatId, inputState);
    }

    public void removeUserAllCredentials(Long chatId) {
        telegramUserService.removeAllCredentials(chatId);
    }

    public List<String> getCredentialsEmailsList(Long chatId) {
        return telegramUserService.getCredentialsEmailsList(chatId);
    }

    public void sendDefaultSpeculativeItemsAsMessages(Long chatId) {
        List<? extends Item> speculativeItems = itemService.getAllSpeculativeItemsByExpectedProfit(50, 40, 0, 15000);
        log.debug("Speculative items amount: {}", speculativeItems.size());
        for (Item item : speculativeItems) {
            telegramBotClientService.sendText(String.valueOf(chatId), getItemString(item));
        }
    }

    public void sendOwnedSpeculativeItemsAsMessages(Long chatId, String email) {

        AuthorizationDTO authorizationDTO = ubiUserService.getAuthorizationDTOFromDbOrThrow(String.valueOf(chatId), email);


        List<? extends Item> speculativeItems = itemService.getOwnedSpeculativeItemsByExpectedProfit(authorizationDTO,50, 40, 0, 15000);

        log.debug("Speculative items amount: {}", speculativeItems.size());
        for (Item item : speculativeItems) {
            telegramBotClientService.sendText(String.valueOf(chatId), getItemString(item));
        }
    }

    private String getItemString(Item item) {
        String name = item.getName();
        String maxBuyPrice = String.valueOf(item.getMaxBuyPrice());
        String buyOrders = String.valueOf(item.getBuyOrdersCount());
        String minSellPrice = String.valueOf(item.getMinSellPrice());
        String sellOrders = String.valueOf(item.getSellOrdersCount());
        String expectedProfit = String.valueOf(item.getExpectedProfit());
        String expectedProfitPercentage = String.valueOf(item.getExpectedProfitPercentage());
        String lastSoldAt = item.getLastSoldAt().toString();
        String lastSoldPrice = String.valueOf(item.getLastSoldPrice());
        String pictureUrl = item.getAssetUrl();

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n")
                .append("Min sell price: ").append(minSellPrice).append("\n")
                .append("Sell orders: ").append(sellOrders).append("\n")
                .append("Max buy price: ").append(maxBuyPrice).append("\n")
                .append("Buy orders: ").append(buyOrders).append("\n")
                .append("Expected profit: ").append(expectedProfit).append("\n")
                .append("Expected profit percentage: ").append(expectedProfitPercentage).append("\n")
                .append("Last sold price: ").append(lastSoldPrice).append("\n")
                .append("Last sold at: ").append(lastSoldAt).append("\n")
                .append("Picture: ").append(pictureUrl).append("\n");

        return sb.toString();
    }
}
