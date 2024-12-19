package github.ricemonger.marketplace.databases.postgres.entities.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Boolean privateNotificationsEnabledFlag = false;

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

    public UserEntity(Long userId) {
        this.id = userId;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof UserEntity entity) {
            boolean itemFiltersAreEqual = itemFilters.size() == entity.itemFilters.size() &&
                                          itemFilters.stream().allMatch(itemFilter -> entity.itemFilters.stream().anyMatch(itemFilter::isFullyEqualExceptUser));

            boolean tradeByFiltersManagersAreEqual = tradeByFiltersManagers.size() == entity.tradeByFiltersManagers.size() &&
                                                     tradeByFiltersManagers.stream().allMatch(tradeByFiltersManager -> entity.tradeByFiltersManagers.stream().anyMatch(tradeByFiltersManager::isFullyEqualExceptUser));

            boolean tradeByItemIdManagersAreEqual = tradeByItemIdManagers.size() == entity.tradeByItemIdManagers.size() &&
                                                    tradeByItemIdManagers.stream().allMatch(tradeByItemIdManager -> entity.tradeByItemIdManagers.stream().anyMatch(tradeByItemIdManager::isFullyEqualExceptUser));

            boolean itemShowAppliedFiltersAreEqual = itemShowAppliedFilters.size() == entity.itemShowAppliedFilters.size() &&
                                                     itemShowAppliedFilters.stream().allMatch(itemFilter -> entity.itemShowAppliedFilters.stream().anyMatch(itemFilter::isFullyEqualExceptUser));

            return Objects.equals(id, entity.id) &&
                   telegramUser.isFullyEqualExceptUser(entity.telegramUser) &&
                   ubiAccountAuthorizationEntry.isFullyEqualExceptUser(entity.ubiAccountAuthorizationEntry) &&
                   itemFiltersAreEqual &&
                   tradeByFiltersManagersAreEqual &&
                   tradeByItemIdManagersAreEqual &&
                   Objects.equals(publicNotificationsEnabledFlag, entity.publicNotificationsEnabledFlag) &&
                   Objects.equals(privateNotificationsEnabledFlag, entity.privateNotificationsEnabledFlag) &&
                   Objects.equals(itemShowNameFlag, entity.itemShowNameFlag) &&
                   Objects.equals(itemShowItemTypeFlag, entity.itemShowItemTypeFlag) &&
                   Objects.equals(itemShowMaxBuyPrice, entity.itemShowMaxBuyPrice) &&
                   Objects.equals(itemShowBuyOrdersCountFlag, entity.itemShowBuyOrdersCountFlag) &&
                   Objects.equals(itemShowMinSellPriceFlag, entity.itemShowMinSellPriceFlag) &&
                   Objects.equals(itemsShowSellOrdersCountFlag, entity.itemsShowSellOrdersCountFlag) &&
                   Objects.equals(itemShowPictureFlag, entity.itemShowPictureFlag) &&
                   itemShowAppliedFiltersAreEqual &&
                   Objects.equals(newManagersAreActiveFlag, entity.newManagersAreActiveFlag) &&
                   Objects.equals(managingEnabledFlag, entity.managingEnabledFlag);
        }
        return false;
    }
}
