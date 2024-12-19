package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.services.entity_factories.user.ItemFilterEntityFactory;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.ItemShownFieldsSettings;
import github.ricemonger.utils.DTOs.TelegramUser;
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

    @Enumerated(EnumType.ORDINAL)
    private InputState inputState = InputState.BASE;
    @Enumerated(EnumType.ORDINAL)
    private InputGroup inputGroup = InputGroup.BASE;

    private Integer itemShowMessagesLimit = 50;
    private Boolean itemShowFewInMessageFlag = false;

    public void setShowItemFieldsSettings(ItemShownFieldsSettings settings) {
        this.user.setItemShowNameFlag(settings.isItemShowNameFlag());
        this.user.setItemShowItemTypeFlag(settings.isItemShowItemTypeFlag());
        this.user.setItemShowMaxBuyPrice(settings.isItemShowMaxBuyPrice());
        this.user.setItemShowBuyOrdersCountFlag(settings.isItemShowBuyOrdersCountFlag());
        this.user.setItemShowMinSellPriceFlag(settings.isItemShowMinSellPriceFlag());
        this.user.setItemsShowSellOrdersCountFlag(settings.isItemsShowSellOrdersCountFlag());
        this.user.setItemShowPictureFlag(settings.isItemShowPictureFlag());
    }

    public List<ItemFilterEntity> getItemShowAppliedFilters() {
        return this.user.getItemShowAppliedFilters();
    }

    public void setItemShowAppliedFilters(List<ItemFilterEntity> filters) {
        if (filters == null) {
            this.user.getItemShowAppliedFilters().clear();
        } else {
            this.user.getItemShowAppliedFilters().clear();
            this.user.getItemShowAppliedFilters().addAll(filters);
        }
    }

    public void setNewManagersAreActiveFlag(boolean flag) {
        this.user.setNewManagersAreActiveFlag(flag);
    }

    public void setManagingEnabledFlag(boolean flag) {
        this.user.setManagingEnabledFlag(flag);
    }

    public void setFields(TelegramUser telegramUser, ItemFilterEntityFactory itemFilterEntityFactory) {
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
            this.user.getItemShowAppliedFilters().addAll(telegramUser.getItemShowAppliedFilters().stream().map(itemFilter -> itemFilterEntityFactory.createEntityForTelegramUserChatId(telegramUser.getChatId(), itemFilter)).toList());
        }
        this.user.setNewManagersAreActiveFlag(telegramUser.isNewManagersAreActiveFlag());
        this.user.setManagingEnabledFlag(telegramUser.isManagingEnabledFlag());
    }
}
