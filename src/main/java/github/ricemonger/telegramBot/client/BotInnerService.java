package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.databases.neo4j.services.ItemService;
import github.ricemonger.marketplace.databases.neo4j.services.TelegramLinkedUserService;
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

    private final TelegramLinkedUserService telegramLinkedUserService;

    private final ItemService itemService;

    public void askFromInlineKeyboard(UpdateInfo updateInfo, String text, int buttonsInLine, CallbackButton[] buttons) {
        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    public void sendText(UpdateInfo updateInfo, String answer) {
        telegramBotClientService.sendText(updateInfo, answer);
    }

    public boolean isRegistered(Long chatId) {
        return telegramLinkedUserService.isTelegramUserRegistered(chatId);
    }

    public void registerUser(Long chatId) {
        telegramLinkedUserService.registerTelegramUser(chatId);
    }

    public void addCredentialsFromUserInputs(Long chatId) {
        String fullOrEmail = getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        if (fullOrEmail.contains(":")) {
            String email = fullOrEmail.substring(0, fullOrEmail.indexOf(":"));
            String password = fullOrEmail.substring(fullOrEmail.indexOf(":") + 1);
            telegramLinkedUserService.addCredentials(chatId, email, password);
        }
        else{
            String password = getUserInputByState(chatId, InputState.CREDENTIALS_PASSWORD);
            telegramLinkedUserService.addCredentials(chatId, fullOrEmail, password);
        }

        clearUserInputs(chatId);
    }

    public void removeCredentialsByUserInputs(Long chatId) {
        telegramLinkedUserService.removeCredentialsByUserInputs(chatId);

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
        telegramLinkedUserService.saveUserInput(updateInfo.getChatId(),updateInfo.getInputState(), userInput);
    }

    public void clearUserInputs(Long chatId) {
        telegramLinkedUserService.clearUserInputs(chatId);
    }

    public void setUserNextInputState(Long chatId, InputState inputState) {
        telegramLinkedUserService.setUserNextInputState(chatId, inputState);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) {
        telegramLinkedUserService.setUserNextInputGroup(chatId, inputGroup);
    }

    public String getUserInputByState(Long chatId, InputState inputState){
        return telegramLinkedUserService.getUserInputByStateOrNull(chatId, inputState);
    }

    public void removeUserAllCredentials(Long chatId) {
        telegramLinkedUserService.removeAllCredentials(chatId);
    }

    public List<String> getCredentialsEmailsList(Long chatId) {
        return telegramLinkedUserService.getCredentialsEmailsList(chatId);
    }

    public void sendDefaultSpeculativeItemsAsMessages(Long chatId) {
        List<ItemEntity> speculativeItems = itemService.getSpeculativeItemsByExpectedProfit(50, 40, 0, 15000);
        log.debug("Speculative items amount: {}", speculativeItems.size());
        for (ItemEntity item : speculativeItems) {
            telegramBotClientService.sendText(String.valueOf(chatId), getItemString(item));
        }
    }

    private String getItemString(ItemEntity entity){
        String name = entity.getName();
        String maxBuyPrice = String.valueOf(entity.getMaxBuyPrice());
        String buyOrders = String.valueOf(entity.getBuyOrders());
        String minSellPrice = String.valueOf(entity.getMinSellPrice());
        String sellOrders = String.valueOf(entity.getSellOrders());
        String expectedProfit = String.valueOf(entity.getExpectedProfit());
        String expectedProfitPercentage = String.valueOf(entity.getExpectedProfitPercentage());
        String lastSoldAt = entity.getLastSoldAt().toString();
        String lastSoldPrice = String.valueOf(entity.getLastSoldPrice());
        String pictureUrl = entity.getAssetUrl();

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
