package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Entity
@Table(name = "ubi_account_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsEntity {
    @Id
    private String ubiProfileId;

    private Integer creditAmount;

    private Integer soldIn24h;

    private Integer boughtIn24h;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemid"))
    private List<ItemIdEntity> ownedItems = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemid"))
    private List<ItemResaleLockEntity> resaleLocks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "tradeId", referencedColumnName = "tradeId"))
    private List<TradeEntity> currentSellTrades = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "tradeId", referencedColumnName = "tradeId"))
    private List<TradeEntity> currentBuyTrades = new ArrayList<>();

    public UbiAccountStatsEntity(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

    public boolean isEqual(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UbiAccountStatsEntity entity) {
            return Objects.equals(this.ubiProfileId, entity.ubiProfileId);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UbiAccountStatsEntity entity) {

            boolean ownedItemsIdsAreEqual = ownedItems == null && entity.ownedItems == null || (
                    this.ownedItems != null && entity.ownedItems != null &&
                    this.ownedItems.size() == entity.ownedItems.size() && this.ownedItems.stream().allMatch(item -> entity.ownedItems.stream().anyMatch(item::isEqual)));

            boolean resaleLocksIdsAreEqual = resaleLocks == null && entity.resaleLocks == null || (
                    this.resaleLocks != null && entity.resaleLocks != null &&
                    this.resaleLocks.size() == entity.resaleLocks.size() && this.resaleLocks.stream().allMatch(item -> entity.resaleLocks.stream().anyMatch(item::isEqual)));

            boolean currentSellTradesIdsAreEqual = currentSellTrades == null && entity.currentSellTrades == null || (
                    this.currentSellTrades != null && entity.currentSellTrades != null &&
                    this.currentSellTrades.size() == entity.currentSellTrades.size() && this.currentSellTrades.stream().allMatch(item -> entity.currentSellTrades.stream().anyMatch(item::isEqual)));

            boolean currentBuyTradesIdsAreEqual = currentBuyTrades == null && entity.currentBuyTrades == null || (
                    this.currentBuyTrades != null && entity.currentBuyTrades != null &&
                    this.currentBuyTrades.size() == entity.currentBuyTrades.size() && this.currentBuyTrades.stream().allMatch(item -> entity.currentBuyTrades.stream().anyMatch(item::isEqual)));

            return isEqual(entity) &&
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
}
