package github.ricemonger.marketplace.graphQl.graphsDTOs.config_query_marketplace.marketplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagGroups {

    private String type;

    private String[] values;

    private String displayName;
}
