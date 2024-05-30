package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.enums.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


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
    @Column(columnDefinition = "TEXT")
    private String tags;
    private ItemType type;

    private int maxBuyPrice;
    private int buyOrdersCount;

    private int minSellPrice;
    private int sellOrdersCount;

    private Date lastSoldAt;
    private int lastSoldPrice;

    private int limitMinPrice;
    private int limitMaxPrice;
}
