package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.enums.ItemType;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Entity(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity{
    @Id
    private String itemId;
    private String assetUrl;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String tags;
    private ItemType type;

    private int maxBuyPrice;
    private int buyOrdersCount;

    private int minSellPrice;
    private int sellOrdersCount;

    private Date lastSoldAt;
    private int lastSoldPrice;

    private int expectedProfit;
    private int expectedProfitPercentage;
}
