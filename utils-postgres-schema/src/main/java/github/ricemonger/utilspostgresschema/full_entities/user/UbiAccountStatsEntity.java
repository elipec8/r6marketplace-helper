package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.id_entities.item.IdItemEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdItemResaleLockEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdTradeEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdUbiAccountStatsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsEntity extends IdUbiAccountStatsEntity {
    @Column(name = "credit_amount")
    private Integer creditAmount;

    @Column(name = "sold_in_24h")
    private Integer soldIn24h;

    @Column(name = "bought_in_24h")
    private Integer boughtIn24h;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "item_id"))
    private List<ItemEntity> ownedItems = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "item_id"))
    private List<ItemResaleLockEntity> resaleLocks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "trade_id", referencedColumnName = "trade_id"))
    private List<TradeEntity> currentSellTrades = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "trade_id", referencedColumnName = "trade_id"))
    private List<TradeEntity> currentBuyTrades = new ArrayList<>();
}
