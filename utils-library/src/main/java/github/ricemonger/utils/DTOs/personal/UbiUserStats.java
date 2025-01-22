package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiUserStats {
    private int creditAmount;
    private List<UbiTrade> currentOrders;
    private List<UbiTrade> finishedOrders;
    private List<String> ownedItemsIds;
    private UserTradesLimitations userTradesLimitations;
}
