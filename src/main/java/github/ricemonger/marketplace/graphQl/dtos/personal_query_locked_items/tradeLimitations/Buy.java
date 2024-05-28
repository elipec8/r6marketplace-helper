package github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.tradeLimitations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buy {
    private int resolvedTransactionCount;
    private int activeTransactionCount;
}
