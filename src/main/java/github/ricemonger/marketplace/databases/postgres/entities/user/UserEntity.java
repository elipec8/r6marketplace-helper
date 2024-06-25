package github.ricemonger.marketplace.databases.postgres.entities.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = true)
    private TelegramUserEntity telegramUser;

    private Boolean publicNotificationsEnabledFlag = true;

    private Integer itemShowMessagesLimit = 50;
    private Boolean itemShowFewInMessageFlag = false;

    private Boolean itemShowNameFlag = true;
    private Boolean itemShowItemTypeFlag = true;
    private Boolean itemShowMaxBuyPrice = true;
    private Boolean itemShowBuyOrdersCountFlag = true;
    private Boolean itemShowMinSellPriceFlag = true;
    private Boolean itemsShowSellOrdersCountFlag = true;
    private Boolean itemShowPictureFlag = true;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_item_show_applied_item_filter",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "item_filter_name", referencedColumnName = "name"))
    private List<ItemFilterEntity> itemShowAppliedFilters;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = true)
    private UbiAccountEntity ubiAccount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemFilterEntity> itemFilters = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TradeManagerByItemFiltersEntity> tradeManagersByItemFilters = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TradeManagerByItemIdEntity> tradeManagersByItemId = new ArrayList<>();
}
