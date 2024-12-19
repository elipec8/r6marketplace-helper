package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Entity(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    private String itemId;
    private String assetUrl;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "item_tags",
            joinColumns = {@JoinColumn(name = "itemId", referencedColumnName = "itemId")},
            inverseJoinColumns = @JoinColumn(name = "tagValue", referencedColumnName = "tag_value"))
    private List<TagEntity> tags;

    private ItemRarity rarity;

    private ItemType type;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;

    private LocalDateTime lastSoldAt;
    private Integer lastSoldPrice;

    // Sale history fields

    private Integer monthAveragePrice;
    private Integer monthMedianPrice;
    private Integer monthMaxPrice;
    private Integer monthMinPrice;
    private Integer monthSalesPerDay;
    private Integer monthSales;

    private Integer dayAveragePrice;
    private Integer dayMedianPrice;
    private Integer dayMaxPrice;
    private Integer dayMinPrice;
    private Integer daySales;

    private Long priorityToSellByMaxBuyPrice; //updated with every item stats update, not recalculation
    private Long priorityToSellByNextFancySellPrice; //updated with every item stats update, not recalculation

    private Long priorityToBuyByMinSellPrice; //updated with every item stats update, not recalculation

    @Column(name = "priority_to_buy_in_1_hour")
    private Long priorityToBuyIn1Hour;
    @Column(name = "priority_to_buy_in_6_hours")
    private Long priorityToBuyIn6Hours;
    @Column(name = "priority_to_buy_in_24_hours")
    private Long priorityToBuyIn24Hours;
    @Column(name = "priority_to_buy_in_168_hours")
    private Long priorityToBuyIn168Hours;
    @Column(name = "priority_to_buy_in_720_hours")
    private Long priorityToBuyIn720Hours;

    @Column(name = "price_to_buy_in_1_hour")
    private Integer priceToBuyIn1Hour;
    @Column(name = "price_to_buy_in_6_hours")
    private Integer priceToBuyIn6Hours;
    @Column(name = "price_to_buy_in_24_hours")
    private Integer priceToBuyIn24Hours;
    @Column(name = "price_to_buy_in_168_hours")
    private Integer priceToBuyIn168Hours;
    @Column(name = "price_to_buy_in_720_hours")
    private Integer priceToBuyIn720Hours;

    public ItemEntity(String itemId) {
        this.itemId = itemId;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemEntity itemEntity) {

            boolean tagsAreEqual = this.tags.size() == itemEntity.tags.size() &&
                                   this.tags.stream().allMatch(tag -> itemEntity.tags.stream().anyMatch(tag::isFullyEqual));

            return Objects.equals(itemId, itemEntity.itemId) &&
                   Objects.equals(assetUrl, itemEntity.assetUrl) &&
                   Objects.equals(name, itemEntity.name) &&
                   tagsAreEqual &&
                   rarity == itemEntity.rarity &&
                   type == itemEntity.type &&
                   Objects.equals(maxBuyPrice, itemEntity.maxBuyPrice) &&
                   Objects.equals(buyOrdersCount, itemEntity.buyOrdersCount) &&
                   Objects.equals(minSellPrice, itemEntity.minSellPrice) &&
                   Objects.equals(sellOrdersCount, itemEntity.sellOrdersCount) &&
                   Objects.equals(lastSoldAt, itemEntity.lastSoldAt) &&
                   Objects.equals(lastSoldPrice, itemEntity.lastSoldPrice) &&
                   Objects.equals(monthAveragePrice, itemEntity.monthAveragePrice) &&
                   Objects.equals(monthMedianPrice, itemEntity.monthMedianPrice) &&
                   Objects.equals(monthMaxPrice, itemEntity.monthMaxPrice) &&
                   Objects.equals(monthMinPrice, itemEntity.monthMinPrice) &&
                   Objects.equals(monthSalesPerDay, itemEntity.monthSalesPerDay) &&
                   Objects.equals(monthSales, itemEntity.monthSales) &&
                   Objects.equals(dayAveragePrice, itemEntity.dayAveragePrice) &&
                   Objects.equals(dayMedianPrice, itemEntity.dayMedianPrice) &&
                   Objects.equals(dayMaxPrice, itemEntity.dayMaxPrice) &&
                   Objects.equals(dayMinPrice, itemEntity.dayMinPrice) &&
                   Objects.equals(daySales, itemEntity.daySales) &&
                   Objects.equals(priorityToSellByMaxBuyPrice, itemEntity.priorityToSellByMaxBuyPrice) &&
                   Objects.equals(priorityToSellByNextFancySellPrice, itemEntity.priorityToSellByNextFancySellPrice) &&
                   Objects.equals(priorityToBuyByMinSellPrice, itemEntity.priorityToBuyByMinSellPrice) &&
                   Objects.equals(priorityToBuyIn1Hour, itemEntity.priorityToBuyIn1Hour) &&
                   Objects.equals(priorityToBuyIn6Hours, itemEntity.priorityToBuyIn6Hours) &&
                   Objects.equals(priorityToBuyIn24Hours, itemEntity.priorityToBuyIn24Hours) &&
                   Objects.equals(priorityToBuyIn168Hours, itemEntity.priorityToBuyIn168Hours) &&
                   Objects.equals(priorityToBuyIn720Hours, itemEntity.priorityToBuyIn720Hours) &&
                   Objects.equals(priceToBuyIn1Hour, itemEntity.priceToBuyIn1Hour) &&
                   Objects.equals(priceToBuyIn6Hours, itemEntity.priceToBuyIn6Hours) &&
                   Objects.equals(priceToBuyIn24Hours, itemEntity.priceToBuyIn24Hours) &&
                   Objects.equals(priceToBuyIn168Hours, itemEntity.priceToBuyIn168Hours) &&
                   Objects.equals(priceToBuyIn720Hours, itemEntity.priceToBuyIn720Hours);
        }
        return false;
    }
}
