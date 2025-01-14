package github.ricemonger.fast_sell_trade_manager.services.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PotentialTrade {
    private String itemId;
    private Integer price;
    private Integer monthMedianPriceDifference;
    private Integer monthMedianPriceDifferencePercentage;
    private boolean sellByMaxBuyPrice;
}
