package github.ricemonger.utilspostgresschema.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "helper_user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "user")
    private TelegramUserEntity telegramUser;

    @OneToOne(mappedBy = "user")
    private UbiAccountEntryEntity ubiAccountEntry;

    @OneToMany(mappedBy = "user")
    private List<ItemFilterEntity> itemFilters = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TradeByFiltersManagerEntity> tradeByFiltersManagers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TradeByItemIdManagerEntity> tradeByItemIdManagers = new ArrayList<>();

    @Column(name = "public_notifications_enabled_flag")
    private Boolean publicNotificationsEnabledFlag = true;
    @Column(name = "private_notifications_enabled_flag")
    private Boolean privateNotificationsEnabledFlag = false;

    @Column(name = "item_show_name_flag")
    private Boolean itemShowNameFlag = true;
    @Column(name = "item_show_item_type_flag")
    private Boolean itemShowItemTypeFlag = true;
    @Column(name = "item_show_max_buy_price")
    private Boolean itemShowMaxBuyPrice = true;
    @Column(name = "item_show_buy_orders_count_flag")
    private Boolean itemShowBuyOrdersCountFlag = true;
    @Column(name = "item_show_min_sell_price_flag")
    private Boolean itemShowMinSellPriceFlag = true;
    @Column(name = "item_show_sell_orders_count_flag")
    private Boolean itemsShowSellOrdersCountFlag = true;
    @Column(name = "item_show_picture_flag")
    private Boolean itemShowPictureFlag = true;

    @OneToMany
    @JoinTable(name = "user_item_show_applied_item_filter",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "item_filter_name", referencedColumnName = "name"))
    private List<ItemFilterEntity> itemShowAppliedFilters = new ArrayList<>();

    @Column(name = "new_managers_are_active_flag")
    private Boolean newManagersAreActiveFlag = true;
    @Column(name = "managing_enabled_flag")
    private Boolean managingEnabledFlag = true;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEntity userEntity)) {
            return false;
        }
        return Objects.equals(id, userEntity.id);
    }
}
