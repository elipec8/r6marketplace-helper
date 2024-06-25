package github.ricemonger.marketplace.databases.postgres.entities.user;

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

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "telegramUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelegramUserInputEntity> telegramUserInputs;

    private InputState inputState = InputState.BASE;
    private InputGroup inputGroup = InputGroup.BASE;

    public TelegramUserEntity(TelegramUser telegramUser) {
        this.chatId = telegramUser.getChatId();
        this.inputState = telegramUser.getInputState();
        this.inputGroup = telegramUser.getInputGroup();
        this.user.setPublicNotificationsEnabledFlag(telegramUser.isPublicNotificationsEnabledFlag());
        this.user.setItemShowMessagesLimit(telegramUser.getItemShowMessagesLimit());
        this.user.setItemShowFewInMessageFlag(telegramUser.isItemShowFewInMessageFlag());
        this.user.setItemShowNameFlag(telegramUser.isItemShowNameFlag());
        this.user.setItemShowItemTypeFlag(telegramUser.isItemShowItemTypeFlag());
        this.user.setItemShowMaxBuyPrice(telegramUser.isItemShowMaxBuyPrice());
        this.user.setItemShowBuyOrdersCountFlag(telegramUser.isItemShowBuyOrdersCountFlag());
        this.user.setItemShowMinSellPriceFlag(telegramUser.isItemShowMinSellPriceFlag());
        this.user.setItemsShowSellOrdersCountFlag(telegramUser.isItemsShowSellOrdersCountFlag());
        this.user.setItemShowPictureFlag(telegramUser.isItemShowPictureFlag());
        if (telegramUser.getItemShowAppliedFilters() != null) {
            this.user.setItemShowAppliedFilters(telegramUser.getItemShowAppliedFilters().stream().map(ItemFilterEntity::new).toList());
        }
    }

    public TelegramUser toTelegramUser() {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(this.chatId);
        telegramUser.setInputState(this.inputState);
        telegramUser.setInputGroup(this.inputGroup);
        telegramUser.setPublicNotificationsEnabledFlag(this.user.getPublicNotificationsEnabledFlag());
        telegramUser.setItemShowMessagesLimit(this.user.getItemShowMessagesLimit());
        telegramUser.setItemShowFewInMessageFlag(this.user.getItemShowFewInMessageFlag());
        telegramUser.setItemShowNameFlag(this.user.getItemShowNameFlag());
        telegramUser.setItemShowItemTypeFlag(this.user.getItemShowItemTypeFlag());
        telegramUser.setItemShowMaxBuyPrice(this.user.getItemShowMaxBuyPrice());
        telegramUser.setItemShowBuyOrdersCountFlag(this.user.getItemShowBuyOrdersCountFlag());
        telegramUser.setItemShowMinSellPriceFlag(this.user.getItemShowMinSellPriceFlag());
        telegramUser.setItemsShowSellOrdersCountFlag(this.user.getItemsShowSellOrdersCountFlag());
        telegramUser.setItemShowPictureFlag(this.user.getItemShowPictureFlag());
        if (this.user.getItemShowAppliedFilters() != null) {
            telegramUser.setItemShowAppliedFilters(this.user.getItemShowAppliedFilters().stream().map(ItemFilterEntity::toItemFilter).toList());
        }
        return telegramUser;
    }

    public void setShowItemFieldsSettings(ItemShownFieldsSettings settings) {
        this.user.setItemShowNameFlag(settings.isItemShowNameFlag());
        this.user.setItemShowItemTypeFlag(settings.isItemShowItemTypeFlag());
        this.user.setItemShowMaxBuyPrice(settings.isItemShowMaxBuyPrice());
        this.user.setItemShowBuyOrdersCountFlag(settings.isItemShowBuyOrdersCountFlag());
        this.user.setItemShowMinSellPriceFlag(settings.isItemShowMinSellPriceFlag());
        this.user.setItemsShowSellOrdersCountFlag(settings.isItemsShowSellOrdersCountFlag());
        this.user.setItemShowPictureFlag(settings.isItemShowPictureFlag());
    }

    public ItemShowSettings toItemShowSettings() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowMessagesLimit(this.user.getItemShowMessagesLimit());
        itemShowSettings.setItemShowFewInMessageFlag(this.user.getItemShowFewInMessageFlag());
        itemShowSettings.setItemShowNameFlag(this.user.getItemShowNameFlag());
        itemShowSettings.setItemShowItemTypeFlag(this.user.getItemShowItemTypeFlag());
        itemShowSettings.setItemShowMaxBuyPrice(this.user.getItemShowMaxBuyPrice());
        itemShowSettings.setItemShowBuyOrdersCountFlag(this.user.getItemShowBuyOrdersCountFlag());
        itemShowSettings.setItemShowMinSellPriceFlag(this.user.getItemShowMinSellPriceFlag());
        itemShowSettings.setItemsShowSellOrdersCountFlag(this.user.getItemsShowSellOrdersCountFlag());
        itemShowSettings.setItemShowPictureFlag(this.user.getItemShowPictureFlag());
        if (this.user.getItemShowAppliedFilters() != null) {
            itemShowSettings.setItemShowAppliedFilters(this.user.getItemShowAppliedFilters().stream().map(ItemFilterEntity::toItemFilter).toList());
        }
        return itemShowSettings;
    }

    public void setItemShowFewInMessageFlag(boolean flag) {
        this.user.setItemShowFewInMessageFlag(flag);
    }

    public void setItemShowMessagesLimit(Integer limit) {
        this.user.setItemShowMessagesLimit(limit);
    }

    public List<ItemFilterEntity> getItemShowAppliedFilters() {
        return this.user.getItemShowAppliedFilters();
    }

    public void setItemShowAppliedFilters(List<ItemFilterEntity> filters) {
        this.user.setItemShowAppliedFilters(filters);
    }
}
