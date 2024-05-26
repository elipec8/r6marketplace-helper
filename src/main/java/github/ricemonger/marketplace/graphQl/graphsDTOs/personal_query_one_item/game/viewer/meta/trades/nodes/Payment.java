package github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_one_item.game.viewer.meta.trades.nodes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private int price;
    private int transactionFee;
}
