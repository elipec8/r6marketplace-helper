package github.ricemonger.marketplace.graphQl.client_services.common_query_items.DTO.marketableItems.node.marketData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyStats {

    private Integer highestPrice;

    private Integer activeCount;
}
