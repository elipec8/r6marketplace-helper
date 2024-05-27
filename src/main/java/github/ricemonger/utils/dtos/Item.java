package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String itemId;
    private String assetUrl;
    private String name;
    private List<String> tags;
    private ItemType type;

    private int maxBuyPrice;
    private int buyOrdersCount;

    private int minSellPrice;
    private int sellOrdersCount;

    private Date lastSoldAt;
    private int lastSoldPrice;

    private boolean isOwned;

    private int limitMinPrice;
    private int limitMaxPrice;

    private List<Trade> trades;
}
