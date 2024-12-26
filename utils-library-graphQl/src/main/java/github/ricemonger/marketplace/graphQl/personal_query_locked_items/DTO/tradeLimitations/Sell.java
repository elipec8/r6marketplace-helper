package github.ricemonger.marketplace.graphQl.personal_query_locked_items.DTO.tradeLimitations;

import github.ricemonger.marketplace.graphQl.personal_query_locked_items.DTO.tradeLimitations.sell.ResaleLocks;
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
