package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.marketplace.databases.neo4j.enums.ItemType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    @Column(name = "item_full_id")
    private String itemFullId;

    private String assetUrl;

    private String name;

    private ItemType type;

    private int maxBuyPrice;

    private int buyOrdersCount;

    private int minSellPrice;

    private int sellOrdersCount;

    private int expectedProfit;

    private int expectedProfitPercentage;

    private Date lastSoldAt;

    private int lastSoldPrice;

    private int monthAveragePrice;
    private int monthMedianPrice;
    private int monthMaxPrice;
    private int monthMinPrice;
    private int monthSalesPerDay;
    private int monthLowPriceSalesPerDay;
    private int monthHighPriceSalesPerDay;

    private int dayAveragePrice;
    private int dayMedianPrice;
    private int dayMaxPrice;
    private int dayMinPrice;
    private int daySales;
    private int dayLowPriceSales;
    private int dayHighPriceSales;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="items_tags",
            joinColumns=  @JoinColumn(name="item_id", referencedColumnName="item_full_id"),
            inverseJoinColumns= @JoinColumn(name="tag", referencedColumnName="tag"))
    private Set<TagEntity> tags = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<ItemSaleEntity> sales = new HashSet<>();

}
