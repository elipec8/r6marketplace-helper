package github.ricemonger.marketplace.graphQl.personal_query_locked_items.DTO.tradeLimitations.sell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResaleLocks {
    private String itemId;
    private String expiresAt;
}
