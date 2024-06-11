package github.ricemonger.utils.dtos;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUser {
    private String chatId;
    private InputState inputState;
    private InputGroup inputGroup;
    private boolean publicNotificationsEnabledFlag;

    private Integer itemShowMessagesLimit = 50;
    private boolean itemShowFewInMessageFlag = false;

    private boolean itemShowNameFlag = true;
    private boolean itemShowItemTypeFlag = true;
    private boolean itemShowMaxBuyPrice = true;
    private boolean itemShowBuyOrdersCountFlag = true;
    private boolean itemShowMinSellPriceFlag = true;
    private boolean itemsShowSellOrdersCountFlag = true;
    private boolean itemShowPictureFlag = true;

    private List<ItemFilter> itemShowAppliedFilters;

    public TelegramUser(Long chatId) {
        this.chatId = String.valueOf(chatId);
        this.inputState = InputState.BASE;
        this.inputGroup = InputGroup.BASE;
        this.publicNotificationsEnabledFlag = true;
    }
}
