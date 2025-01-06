package github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLimitations {
    private Integer minPrice;
    private Integer maxPrice;
}
