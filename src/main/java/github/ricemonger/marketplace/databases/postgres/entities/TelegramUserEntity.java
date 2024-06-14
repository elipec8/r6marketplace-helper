package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "telegram_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserEntity {
    @Id
    private String chatId;

    private InputState inputState = InputState.BASE;
    private InputGroup inputGroup = InputGroup.BASE;

    private boolean publicNotificationsEnabledFlag = true;

    private Integer itemShowMessagesLimit = 50;
    private boolean itemShowFewInMessageFlag = false;

    private boolean itemShowNameFlag = true;
    private boolean itemShowItemTypeFlag = true;
    private boolean itemShowMaxBuyPrice = true;
    private boolean itemShowBuyOrdersCountFlag = true;
    private boolean itemShowMinSellPriceFlag = true;
    private boolean itemsShowSellOrdersCountFlag = true;
    private boolean itemShowPictureFlag = true;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "telegram_user_item_filter",
            joinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "chatId")},
            inverseJoinColumns = @JoinColumn(name = "item_filter_name", referencedColumnName = "name"))
    private List<ItemFilterEntity> itemShowAppliedFilters;

    public void setShowItemSettings(ItemShownFieldsSettings settings) {
        this.itemShowNameFlag = settings.isItemShowNameFlag();
        this.itemShowItemTypeFlag = settings.isItemShowItemTypeFlag();
        this.itemShowMaxBuyPrice = settings.isItemShowMaxBuyPrice();
        this.itemShowBuyOrdersCountFlag = settings.isItemShowBuyOrdersCountFlag();
        this.itemShowMinSellPriceFlag = settings.isItemShowMinSellPriceFlag();
        this.itemsShowSellOrdersCountFlag = settings.isItemsShowSellOrdersCountFlag();
        this.itemShowPictureFlag = settings.isItemShowPictureFlag();
    }
}
