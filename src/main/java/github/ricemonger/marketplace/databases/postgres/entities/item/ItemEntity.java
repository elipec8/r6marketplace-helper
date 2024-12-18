package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.Item;
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

    public ItemEntity(String itemId) {
        this.itemId = itemId;
    }

    public ItemEntity(Item item, Collection<TagEntity> tageEntities) {
        this.itemId = item.getItemId();
        this.assetUrl = item.getAssetUrl();
        this.name = item.getName();

        List<TagEntity> itemTags = new ArrayList<>();
        if (item.getTags() != null && tageEntities != null && !tageEntities.isEmpty()) {
            for (TagEntity tag : tageEntities) {
                if (item.getTags().contains(tag.getValue())) {
                    itemTags.add(tag);
                }
            }
        }
        this.tags = itemTags;

        this.rarity = item.getRarity();
        this.type = item.getType();

        this.maxBuyPrice = item.getMaxBuyPrice();
        this.buyOrdersCount = item.getBuyOrdersCount();

        this.minSellPrice = item.getMinSellPrice();
        this.sellOrdersCount = item.getSellOrdersCount();

        this.lastSoldAt = item.getLastSoldAt();
        this.lastSoldPrice = item.getLastSoldPrice();

        this.monthAveragePrice = item.getMonthAveragePrice();
        this.monthMedianPrice = item.getMonthMedianPrice();
        this.monthMaxPrice = item.getMonthMaxPrice();
        this.monthMinPrice = item.getMonthMinPrice();
        this.monthSalesPerDay = item.getMonthSalesPerDay();
        this.monthSales = item.getMonthSales();

        this.dayAveragePrice = item.getDayAveragePrice();
        this.dayMedianPrice = item.getDayMedianPrice();
        this.dayMaxPrice = item.getDayMaxPrice();
        this.dayMinPrice = item.getDayMinPrice();
        this.daySales = item.getDaySales();

        this.priorityToSellByMaxBuyPrice = item.getPriorityToSellByMaxBuyPrice();
        this.priorityToSellByNextFancySellPrice = item.getPriorityToSellByNextFancySellPrice();

        this.priorityToBuyByMinSellPrice = item.getPriorityToBuyByMinSellPrice();

        this.priorityToBuyIn1Hour = item.getPriorityToBuyIn1Hour();
        this.priorityToBuyIn6Hours = item.getPriorityToBuyIn6Hours();
        this.priorityToBuyIn24Hours = item.getPriorityToBuyIn24Hours();
        this.priorityToBuyIn168Hours = item.getPriorityToBuyIn168Hours();
        this.priorityToBuyIn720Hours = item.getPriorityToBuyIn720Hours();

        this.priceToBuyIn1Hour = item.getPriceToBuyIn1Hour();
        this.priceToBuyIn6Hours = item.getPriceToBuyIn6Hours();
        this.priceToBuyIn24Hours = item.getPriceToBuyIn24Hours();
        this.priceToBuyIn168Hours = item.getPriceToBuyIn168Hours();
        this.priceToBuyIn720Hours = item.getPriceToBuyIn720Hours();
    }

    public Item toItem() {
        List<String> tags = new ArrayList<>();
        if (this.tags != null && !this.tags.isEmpty()) {
            tags = List.of(this.tags.stream().map(TagEntity::getValue).toArray(String[]::new));
        }
        Item item = new Item();

        item.setItemId(this.itemId);
        item.setAssetUrl(this.assetUrl);
        item.setName(this.name);

        item.setTags(tags);

        item.setRarity(this.rarity);
        item.setType(this.type);

        item.setMaxBuyPrice(this.maxBuyPrice);
        item.setBuyOrdersCount(this.buyOrdersCount);

        item.setMinSellPrice(this.minSellPrice);
        item.setSellOrdersCount(this.sellOrdersCount);

        item.setLastSoldAt(this.lastSoldAt);
        item.setLastSoldPrice(this.lastSoldPrice);

        item.setMonthAveragePrice(this.monthAveragePrice);
        item.setMonthMedianPrice(this.monthMedianPrice);
        item.setMonthMaxPrice(this.monthMaxPrice);
        item.setMonthMinPrice(this.monthMinPrice);
        item.setMonthSalesPerDay(this.monthSalesPerDay);
        item.setMonthSales(this.monthSales);

        item.setDayAveragePrice(this.dayAveragePrice);
        item.setDayMedianPrice(this.dayMedianPrice);
        item.setDayMaxPrice(this.dayMaxPrice);
        item.setDayMinPrice(this.dayMinPrice);
        item.setDaySales(this.daySales);

        item.setPriorityToSellByMaxBuyPrice(this.priorityToSellByMaxBuyPrice);
        item.setPriorityToSellByNextFancySellPrice(this.priorityToSellByNextFancySellPrice);

        item.setPriorityToBuyByMinSellPrice(this.priorityToBuyByMinSellPrice);

        item.setPriorityToBuyIn1Hour(this.priorityToBuyIn1Hour);
        item.setPriorityToBuyIn6Hours(this.priorityToBuyIn6Hours);
        item.setPriorityToBuyIn24Hours(this.priorityToBuyIn24Hours);
        item.setPriorityToBuyIn168Hours(this.priorityToBuyIn168Hours);
        item.setPriorityToBuyIn720Hours(this.priorityToBuyIn720Hours);

        item.setPriceToBuyIn1Hour(this.priceToBuyIn1Hour);
        item.setPriceToBuyIn6Hours(this.priceToBuyIn6Hours);
        item.setPriceToBuyIn24Hours(this.priceToBuyIn24Hours);
        item.setPriceToBuyIn168Hours(this.priceToBuyIn168Hours);
        item.setPriceToBuyIn720Hours(this.priceToBuyIn720Hours);

        return item;
    }
}
