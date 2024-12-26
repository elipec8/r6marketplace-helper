package github.ricemonger.marketplace.graphQl.client_services.config_query_marketplace.DTO.marketplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tags {
    private String value;
    private String displayName;
}
