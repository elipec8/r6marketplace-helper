package github.ricemonger.utils.DTOs.personal;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
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

    private Boolean publicNotificationsEnabledFlag;

    private Integer itemShowMessagesLimit;
    private Boolean itemShowFewInMessageFlag;

    private Boolean itemShowNameFlag;
    private Boolean itemShowItemTypeFlag;
    private Boolean itemShowMaxBuyPrice;
    private Boolean itemShowBuyOrdersCountFlag;
    private Boolean itemShowMinSellPriceFlag;
    private Boolean itemsShowSellOrdersCountFlag;
    private Boolean itemShowPictureFlag;

    private List<ItemFilter> itemShowAppliedFilters;

    private Boolean newManagersAreActiveFlag;
    private Boolean managingEnabledFlag;

    public TelegramUser(Long chatId) {
        this.chatId = String.valueOf(chatId);
    }
}
