package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "item_id"))
    private List<ItemIdEntity> ownedItems = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "item_id"))
    private List<ItemResaleLockEntity> resaleLocks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "trade_id", referencedColumnName = "trade_id"))
    private List<UbiTradeEntity> currentSellTrades = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")},
            inverseJoinColumns = @JoinColumn(name = "trade_id", referencedColumnName = "trade_id"))
    private List<UbiTradeEntity> currentBuyTrades = new ArrayList<>();

    public UbiAccountStatsEntity(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

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

    public boolean isFullyEqual(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UbiAccountStatsEntity entity) {

            boolean ownedItemsIdsAreEqual = ownedItems == null && entity.ownedItems == null || (
                    this.ownedItems != null && entity.ownedItems != null &&
                    this.ownedItems.size() == entity.ownedItems.size() && this.ownedItems.stream().allMatch(item -> entity.ownedItems.stream().anyMatch(item::equals)));

            boolean resaleLocksIdsAreEqual = resaleLocks == null && entity.resaleLocks == null || (
                    this.resaleLocks != null && entity.resaleLocks != null &&
                    this.resaleLocks.size() == entity.resaleLocks.size() && this.resaleLocks.stream().allMatch(item -> entity.resaleLocks.stream().anyMatch(item::equals)));

            boolean currentSellTradesIdsAreEqual = currentSellTrades == null && entity.currentSellTrades == null || (
                    this.currentSellTrades != null && entity.currentSellTrades != null &&
                    this.currentSellTrades.size() == entity.currentSellTrades.size() && this.currentSellTrades.stream().allMatch(item -> entity.currentSellTrades.stream().anyMatch(item::equals)));

            boolean currentBuyTradesIdsAreEqual = currentBuyTrades == null && entity.currentBuyTrades == null || (
                    this.currentBuyTrades != null && entity.currentBuyTrades != null &&
                    this.currentBuyTrades.size() == entity.currentBuyTrades.size() && this.currentBuyTrades.stream().allMatch(item -> entity.currentBuyTrades.stream().anyMatch(item::equals)));

            return equals(entity) &&
                   Objects.equals(this.creditAmount, entity.creditAmount) &&
                   Objects.equals(this.soldIn24h, entity.soldIn24h) &&
                   Objects.equals(this.boughtIn24h, entity.boughtIn24h) &&
                   ownedItemsIdsAreEqual &&
                   resaleLocksIdsAreEqual &&
                   currentSellTradesIdsAreEqual &&
                   currentBuyTradesIdsAreEqual;
        }
        return false;
    }

    @Override
    public String toString() {
        return "UbiAccountStatsEntity{" +
               "ubiProfileId='" + ubiProfileId + '\'' +
               ", creditAmount=" + creditAmount +
               ", soldIn24h=" + soldIn24h +
               ", boughtIn24h=" + boughtIn24h +
               ", ownedItems=" + ownedItems +
               ", resaleLocks=" + resaleLocks +
               ", currentSellTrades=" + currentSellTrades +
               ", currentBuyTrades=" + currentBuyTrades +
               '}';
    }
}
