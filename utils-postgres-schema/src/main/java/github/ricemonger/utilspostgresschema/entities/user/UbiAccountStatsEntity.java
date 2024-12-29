package github.ricemonger.utilspostgresschema.entities.user;

import github.ricemonger.utilspostgresschema.entities.item.ItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "ubi_account_stats")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsEntity {
    @Id
    @Column(name = "ubi_profile_id")
    private String ubiProfileId;

    @Column(name = "credit_amount")
    private Integer creditAmount;

    @Column(name = "sold_in_24h")
    private Integer soldIn24h;

    @Column(name = "bought_in_24h")
    private Integer boughtIn24h;

    @ManyToMany
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "item_id"))
    private List<ItemEntity> ownedItems = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "item_id"))
    private List<ItemResaleLockEntity> resaleLocks = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "trade_id", referencedColumnName = "trade_id"))
    private List<TradeEntity> currentSellTrades = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "trade_id", referencedColumnName = "trade_id"))
    private List<TradeEntity> currentBuyTrades = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(ubiProfileId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountStatsEntity ubiAccountStatsEntity)) {
            return false;
        }
        return Objects.equals(this.ubiProfileId, ubiAccountStatsEntity.ubiProfileId);
    }
}
