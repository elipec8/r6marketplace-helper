package github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.tradeLimitations;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.tradeLimitations.sell.ResaleLocks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sell {
    private Integer resolvedTransactionCount;
    private Integer activeTransactionCount;
    private List<ResaleLocks> resaleLocks;
}
