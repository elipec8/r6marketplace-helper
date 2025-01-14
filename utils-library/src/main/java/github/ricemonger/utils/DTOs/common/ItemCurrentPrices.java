package github.ricemonger.utils.DTOs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCurrentPrices {
    private String itemId;
    private Integer minSellPrice;
    private Integer maxBuyPrice;
}
