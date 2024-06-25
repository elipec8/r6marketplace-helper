package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;
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

    public TelegramUserEntity(TelegramUser telegramUser) {
        this.chatId = telegramUser.getChatId();
        this.inputState = telegramUser.getInputState();
        this.inputGroup = telegramUser.getInputGroup();
        this.publicNotificationsEnabledFlag = telegramUser.isPublicNotificationsEnabledFlag();
        this.itemShowMessagesLimit = telegramUser.getItemShowMessagesLimit();
        this.itemShowFewInMessageFlag = telegramUser.isItemShowFewInMessageFlag();
        this.itemShowNameFlag = telegramUser.isItemShowNameFlag();
        this.itemShowItemTypeFlag = telegramUser.isItemShowItemTypeFlag();
        this.itemShowMaxBuyPrice = telegramUser.isItemShowMaxBuyPrice();
        this.itemShowBuyOrdersCountFlag = telegramUser.isItemShowBuyOrdersCountFlag();
        this.itemShowMinSellPriceFlag = telegramUser.isItemShowMinSellPriceFlag();
        this.itemsShowSellOrdersCountFlag = telegramUser.isItemsShowSellOrdersCountFlag();
        this.itemShowPictureFlag = telegramUser.isItemShowPictureFlag();
        if (telegramUser.getItemShowAppliedFilters() != null) {
            this.itemShowAppliedFilters = telegramUser.getItemShowAppliedFilters().stream().map(ItemFilterEntity::new).toList();
        }
    }

    public TelegramUser toTelegramUser() {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(this.chatId);
        telegramUser.setInputState(this.inputState);
        telegramUser.setInputGroup(this.inputGroup);
        telegramUser.setPublicNotificationsEnabledFlag(this.publicNotificationsEnabledFlag);
        telegramUser.setItemShowMessagesLimit(this.itemShowMessagesLimit);
        telegramUser.setItemShowFewInMessageFlag(this.itemShowFewInMessageFlag);
        telegramUser.setItemShowNameFlag(this.itemShowNameFlag);
        telegramUser.setItemShowItemTypeFlag(this.itemShowItemTypeFlag);
        telegramUser.setItemShowMaxBuyPrice(this.itemShowMaxBuyPrice);
        telegramUser.setItemShowBuyOrdersCountFlag(this.itemShowBuyOrdersCountFlag);
        telegramUser.setItemShowMinSellPriceFlag(this.itemShowMinSellPriceFlag);
        telegramUser.setItemsShowSellOrdersCountFlag(this.itemsShowSellOrdersCountFlag);
        telegramUser.setItemShowPictureFlag(this.itemShowPictureFlag);
        if (this.itemShowAppliedFilters != null) {
            telegramUser.setItemShowAppliedFilters(this.itemShowAppliedFilters.stream().map(ItemFilterEntity::toItemFilter).toList());
        }
        return telegramUser;
    }

    public void setShowItemFieldsSettings(ItemShownFieldsSettings settings) {
        this.itemShowNameFlag = settings.isItemShowNameFlag();
        this.itemShowItemTypeFlag = settings.isItemShowItemTypeFlag();
        this.itemShowMaxBuyPrice = settings.isItemShowMaxBuyPrice();
        this.itemShowBuyOrdersCountFlag = settings.isItemShowBuyOrdersCountFlag();
        this.itemShowMinSellPriceFlag = settings.isItemShowMinSellPriceFlag();
        this.itemsShowSellOrdersCountFlag = settings.isItemsShowSellOrdersCountFlag();
        this.itemShowPictureFlag = settings.isItemShowPictureFlag();
    }

    public ItemShowSettings toItemShowSettings() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowMessagesLimit(this.itemShowMessagesLimit);
        itemShowSettings.setItemShowFewInMessageFlag(this.itemShowFewInMessageFlag);
        itemShowSettings.setItemShowNameFlag(this.itemShowNameFlag);
        itemShowSettings.setItemShowItemTypeFlag(this.itemShowItemTypeFlag);
        itemShowSettings.setItemShowMaxBuyPrice(this.itemShowMaxBuyPrice);
        itemShowSettings.setItemShowBuyOrdersCountFlag(this.itemShowBuyOrdersCountFlag);
        itemShowSettings.setItemShowMinSellPriceFlag(this.itemShowMinSellPriceFlag);
        itemShowSettings.setItemsShowSellOrdersCountFlag(this.itemsShowSellOrdersCountFlag);
        itemShowSettings.setItemShowPictureFlag(this.itemShowPictureFlag);
        if (this.itemShowAppliedFilters != null) {
            itemShowSettings.setItemShowAppliedFilters(this.itemShowAppliedFilters.stream().map(ItemFilterEntity::toItemFilter).toList());
        }
        return itemShowSettings;
    }
}
