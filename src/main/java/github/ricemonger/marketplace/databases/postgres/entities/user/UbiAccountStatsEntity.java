package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.UbiAccountStats;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity(name = "ubi_account_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsEntity {
    @Id
    private String ubiProfileId;

    private Integer soldIn24h;

    private Integer boughtIn24h;

    private Integer creditAmount;

    @ManyToMany
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemid"))
    private List<ItemEntity> ownedItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_resale_locks",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemId"))
    private List<ItemResaleLockEntity> resaleLocks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_buy_trades",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "tradeId", referencedColumnName = "tradeId"))
    private List<UbiTradeEntity> currentBuyTrades = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_sell_trades",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "tradeId", referencedColumnName = "tradeId"))
    private List<UbiTradeEntity> currentSellTrades = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_finished_trades",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "tradeId", referencedColumnName = "tradeId"))
    private List<UbiTradeEntity> finishedTrades = new ArrayList<>();

    public UbiAccountStatsEntity(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

    public UbiAccountStatsEntity(UbiAccountStats ubiAccountStats, List<ItemEntity> existingItems) {
        this.ubiProfileId = ubiAccountStats.getUbiProfileId();
        this.soldIn24h = ubiAccountStats.getSoldIn24h();
        this.boughtIn24h = ubiAccountStats.getBoughtIn24h();
        this.creditAmount = ubiAccountStats.getCreditAmount();
        this.ownedItems = existingItems.stream().filter(item -> ubiAccountStats.getOwnedItemsIds().contains(item.getItemId())).toList();

        List<ItemResaleLockEntity> updatedResaleLocks = ubiAccountStats.getResaleLocks().stream().map(itemResaleLock -> {
            ItemEntity item = existingItems.stream().filter(i -> i.getItemId().equals(itemResaleLock.getItemId())).findFirst().orElse(null);
            if (item == null) {
                log.error("Item with id {} not found in existing items", itemResaleLock.getItemId());
                return null;
            }
            return new ItemResaleLockEntity(this, item, itemResaleLock.getExpiresAt());
        }).toList();
        this.resaleLocks.clear();
        this.resaleLocks.addAll(updatedResaleLocks);

        List<UbiTradeEntity> updatedCurrentBuyTrades = ubiAccountStats.getCurrentBuyTrades().stream().map(ubiTrade -> {
            ItemEntity item = existingItems.stream().filter(i -> i.getItemId().equals(ubiTrade.getItemId())).findFirst().orElse(null);
            if (item == null) {
                log.error("Item with id {} not found in existing items", ubiTrade.getItemId());
                return null;
            }
            return new UbiTradeEntity(ubiTrade, item);
        }).toList();
        this.currentBuyTrades.clear();
        this.currentBuyTrades.addAll(updatedCurrentBuyTrades);

        List<UbiTradeEntity> updatedCurrentSellTrades = ubiAccountStats.getCurrentSellTrades().stream().map(ubiTrade -> {
            ItemEntity item = existingItems.stream().filter(i -> i.getItemId().equals(ubiTrade.getItemId())).findFirst().orElse(null);
            if (item == null) {
                log.error("Item with id {} not found in existing items", ubiTrade.getItemId());
                return null;
            }
            return new UbiTradeEntity(ubiTrade, item);
        }).toList();
        this.currentSellTrades.clear();
        this.currentSellTrades.addAll(updatedCurrentSellTrades);

        List<UbiTradeEntity> updatedFinishedTrades = ubiAccountStats.getFinishedTrades().stream().map(ubiTrade -> {
            ItemEntity item = existingItems.stream().filter(i -> i.getItemId().equals(ubiTrade.getItemId())).findFirst().orElse(null);
            if (item == null) {
                log.error("Item with id {} not found in existing items", ubiTrade.getItemId());
                return null;
            }
            return new UbiTradeEntity(ubiTrade, item);
        }).toList();
        this.finishedTrades.clear();
        this.finishedTrades.addAll(updatedFinishedTrades);
    }

    public UbiAccountStats toUbiAccountStats() {
        UbiAccountStats ubiAccountStats = new UbiAccountStats();
        ubiAccountStats.setUbiProfileId(this.ubiProfileId);
        ubiAccountStats.setSoldIn24h(this.soldIn24h);
        ubiAccountStats.setBoughtIn24h(this.boughtIn24h);
        ubiAccountStats.setCreditAmount(this.creditAmount);
        ubiAccountStats.setOwnedItemsIds(this.ownedItems.stream().map(ItemEntity::getItemId).toList());
        ubiAccountStats.setResaleLocks(this.resaleLocks.stream().map(ItemResaleLockEntity::toItemResaleLockWithUbiAccount).toList());
        ubiAccountStats.setCurrentBuyTrades(this.currentBuyTrades.stream().map(UbiTradeEntity::toUbiTrade).toList());
        ubiAccountStats.setCurrentSellTrades(this.currentSellTrades.stream().map(UbiTradeEntity::toUbiTrade).toList());
        ubiAccountStats.setFinishedTrades(this.finishedTrades.stream().map(UbiTradeEntity::toUbiTrade).toList());
        return ubiAccountStats;
    }
}
