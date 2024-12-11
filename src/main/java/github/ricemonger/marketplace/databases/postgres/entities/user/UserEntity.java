package github.ricemonger.marketplace.databases.postgres.entities.user;


import github.ricemonger.utils.DTOs.UserForCentralTradeManager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "helper_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private TelegramUserEntity telegramUser;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private UbiAccountEntryEntity ubiAccountAuthorizationEntry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemFilterEntity> itemFilters = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TradeByFiltersManagerEntity> tradeByFiltersManagers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TradeByItemIdManagerEntity> tradeByItemIdManagers = new ArrayList<>();

    private Boolean publicNotificationsEnabledFlag = true;
    private Boolean privateNotificationsEnabledFlag = true;

    private Boolean itemShowNameFlag = true;
    private Boolean itemShowItemTypeFlag = true;
    private Boolean itemShowMaxBuyPrice = true;
    private Boolean itemShowBuyOrdersCountFlag = true;
    private Boolean itemShowMinSellPriceFlag = true;
    private Boolean itemsShowSellOrdersCountFlag = true;
    private Boolean itemShowPictureFlag = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "user_item_show_applied_item_filter",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "itemFilterName", referencedColumnName = "name"))
    private List<ItemFilterEntity> itemShowAppliedFilters = new ArrayList<>();

    private Boolean newManagersAreActiveFlag = true;
    private Boolean managingEnabledFlag = true;

    public UserForCentralTradeManager toUserForCentralTradeManager() {
        UserForCentralTradeManager userForCentralTradeManager = new UserForCentralTradeManager();

        userForCentralTradeManager.setId(id);

        userForCentralTradeManager.setUbiAccountStats(ubiAccountAuthorizationEntry.getUbiAccountStats().toUbiAccountStats());

        userForCentralTradeManager.setUbiSessionId(ubiAccountAuthorizationEntry.getUbiSessionId());
        userForCentralTradeManager.setUbiSpaceId(ubiAccountAuthorizationEntry.getUbiSpaceId());
        userForCentralTradeManager.setUbiAuthTicket(ubiAccountAuthorizationEntry.getUbiAuthTicket());
        userForCentralTradeManager.setUbiTwoFactorAuthTicket(ubiAccountAuthorizationEntry.getUbiTwoFactorAuthTicket());
        userForCentralTradeManager.setUbiRememberDeviceTicket(ubiAccountAuthorizationEntry.getUbiRememberDeviceTicket());
        userForCentralTradeManager.setUbiRememberMeTicket(ubiAccountAuthorizationEntry.getUbiRememberMeTicket());

        userForCentralTradeManager.setChatId(telegramUser.getChatId());
        userForCentralTradeManager.setPrivateNotificationsEnabledFlag(privateNotificationsEnabledFlag);

        userForCentralTradeManager.setTradeByFiltersManagers(tradeByFiltersManagers.stream().map(TradeByFiltersManagerEntity::toTradeByFiltersManager).toList());
        userForCentralTradeManager.setTradeByItemIdManagers(tradeByItemIdManagers.stream().map(TradeByItemIdManagerEntity::toTradeByItemIdManager).toList());

        return userForCentralTradeManager;
    }
}
