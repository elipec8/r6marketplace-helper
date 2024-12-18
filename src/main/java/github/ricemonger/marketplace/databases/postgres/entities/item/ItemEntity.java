package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.ItemEntityDTO;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
}
