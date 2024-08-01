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

import java.util.ArrayList;
import java.util.List;

@Entity(name = "telegram_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserEntity {
    @Id
    private String chatId;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "telegramUser", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TelegramUserInputEntity> telegramUserInputs = new ArrayList<>();

    private InputState inputState = InputState.BASE;
    private InputGroup inputGroup = InputGroup.BASE;

    private Integer itemShowMessagesLimit = 50;
    private Boolean itemShowFewInMessageFlag = false;

    public TelegramUserEntity(String chatId, UserEntity user) {
        this.chatId = chatId;
        this.user = user;
    }

    public TelegramUser toTelegramUser() {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(this.chatId);
        telegramUser.setInputState(this.inputState);
        telegramUser.setInputGroup(this.inputGroup);
        telegramUser.setPublicNotificationsEnabledFlag(this.user.getPublicNotificationsEnabledFlag());
        telegramUser.setItemShowMessagesLimit(this.itemShowMessagesLimit);
        telegramUser.setItemShowFewInMessageFlag(this.itemShowFewInMessageFlag);
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
        itemShowSettings.setItemShowMessagesLimit(this.itemShowMessagesLimit);
        itemShowSettings.setItemShowFewInMessageFlag(this.itemShowFewInMessageFlag);
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
        else{
            itemShowSettings.setItemShowAppliedFilters(new ArrayList<>());
        }
        return itemShowSettings;
    }

    public List<ItemFilterEntity> getItemShowAppliedFilters() {
        return this.user.getItemShowAppliedFilters();
    }

    public void setItemShowAppliedFilters(List<ItemFilterEntity> filters) {
        if(filters == null){
            this.user.getItemShowAppliedFilters().clear();
        }
        else{
            this.user.getItemShowAppliedFilters().clear();
            this.user.getItemShowAppliedFilters().addAll(filters);
        }
    }

    public void setFields(TelegramUser telegramUser) {
        this.chatId = telegramUser.getChatId();
        this.inputState = telegramUser.getInputState();
        this.inputGroup = telegramUser.getInputGroup();
        this.user.setPublicNotificationsEnabledFlag(telegramUser.isPublicNotificationsEnabledFlag());
        this.itemShowMessagesLimit = telegramUser.getItemShowMessagesLimit();
        this.itemShowFewInMessageFlag = telegramUser.isItemShowFewInMessageFlag();
        this.user.setItemShowNameFlag(telegramUser.isItemShowNameFlag());
        this.user.setItemShowItemTypeFlag(telegramUser.isItemShowItemTypeFlag());
        this.user.setItemShowMaxBuyPrice(telegramUser.isItemShowMaxBuyPrice());
        this.user.setItemShowBuyOrdersCountFlag(telegramUser.isItemShowBuyOrdersCountFlag());
        this.user.setItemShowMinSellPriceFlag(telegramUser.isItemShowMinSellPriceFlag());
        this.user.setItemsShowSellOrdersCountFlag(telegramUser.isItemsShowSellOrdersCountFlag());
        this.user.setItemShowPictureFlag(telegramUser.isItemShowPictureFlag());
        if (telegramUser.getItemShowAppliedFilters() != null) {
            this.user.getItemShowAppliedFilters().clear();
            this.user.getItemShowAppliedFilters().addAll(telegramUser.getItemShowAppliedFilters().stream().map(ItemFilterEntity::new).toList());
        }
    }
}
