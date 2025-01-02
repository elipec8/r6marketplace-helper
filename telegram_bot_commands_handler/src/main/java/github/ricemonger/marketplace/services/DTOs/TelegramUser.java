package github.ricemonger.marketplace.services.DTOs;


import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
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
}
