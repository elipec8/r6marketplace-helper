package github.ricemonger.utils.DTOs.personal;


import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetails implements ItemMainFieldsI {
    private String itemId;
    private String assetUrl;
    private String name;
    private List<String> tags;

    private ItemRarity rarity;

    private ItemType type;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;

    private LocalDateTime lastSoldAt;
    private Integer lastSoldPrice;

    private Boolean isOwned;
    private List<UbiTrade> trades;
}
