package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements SoldItemDetails {
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

    public String getTagsAsString() {
        return String.join(",", tags);
    }

    public void setTagsFromString(String tags) {
        this.tags = List.of(tags.split("[,|]"));
    }
}
