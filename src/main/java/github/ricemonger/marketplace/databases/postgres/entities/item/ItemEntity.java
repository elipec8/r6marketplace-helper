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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    private Integer dayAveragePrice;
    private Integer dayMedianPrice;
    private Integer dayMaxPrice;
    private Integer dayMinPrice;
    private Integer daySales;

    @Column(name = "price_to_sell_in_1_hour")
    private Integer priceToSellIn1Hour;
    @Column(name = "price_to_sell_in_6_hours")
    private Integer priceToSellIn6Hours;
    @Column(name = "price_to_sell_in_24_hours")
    private Integer priceToSellIn24Hours;
    @Column(name = "price_to_sell_in_168_hours")
    private Integer priceToSellIn168Hours;

    @Column(name = "price_to_buy_in_1_hour")
    private Integer priceToBuyIn1Hour;
    @Column(name = "price_to_buy_in_6_hours")
    private Integer priceToBuyIn6Hours;
    @Column(name = "price_to_buy_in_24_hours")
    private Integer priceToBuyIn24Hours;
    @Column(name = "price_to_buy_in_168_hours")
    private Integer priceToBuyIn168Hours;

    public ItemEntity(String itemId) {
        this.itemId = itemId;
    }

    public ItemEntity(Item item, Collection<TagEntity> tageEntities) {
        this.itemId = item.getItemId();
        this.assetUrl = item.getAssetUrl();
        this.name = item.getName();

        List<TagEntity> itemTags = new ArrayList<>();
        if (tageEntities != null && !tageEntities.isEmpty()) {
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

        this.dayAveragePrice = item.getDayAveragePrice();
        this.dayMedianPrice = item.getDayMedianPrice();
        this.dayMaxPrice = item.getDayMaxPrice();
        this.dayMinPrice = item.getDayMinPrice();
        this.daySales = item.getDaySales();

        this.priceToSellIn1Hour = item.getPriceToSellIn1Hour();
        this.priceToSellIn6Hours = item.getPriceToSellIn6Hours();
        this.priceToSellIn24Hours = item.getPriceToSellIn24Hours();
        this.priceToSellIn168Hours = item.getPriceToSellIn168Hours();

        this.priceToBuyIn1Hour = item.getPriceToBuyIn1Hour();
        this.priceToBuyIn6Hours = item.getPriceToBuyIn6Hours();
        this.priceToBuyIn24Hours = item.getPriceToBuyIn24Hours();
        this.priceToBuyIn168Hours = item.getPriceToBuyIn168Hours();
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

        item.setDayAveragePrice(this.dayAveragePrice);
        item.setDayMedianPrice(this.dayMedianPrice);
        item.setDayMaxPrice(this.dayMaxPrice);
        item.setDayMinPrice(this.dayMinPrice);
        item.setDaySales(this.daySales);

        item.setPriceToSellIn1Hour(this.priceToSellIn1Hour);
        item.setPriceToSellIn6Hours(this.priceToSellIn6Hours);
        item.setPriceToSellIn24Hours(this.priceToSellIn24Hours);
        item.setPriceToSellIn168Hours(this.priceToSellIn168Hours);

        item.setPriceToBuyIn1Hour(this.priceToBuyIn1Hour);
        item.setPriceToBuyIn6Hours(this.priceToBuyIn6Hours);
        item.setPriceToBuyIn24Hours(this.priceToBuyIn24Hours);
        item.setPriceToBuyIn168Hours(this.priceToBuyIn168Hours);
        return item;
    }
}
