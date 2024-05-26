package github.ricemonger.marketplace.graphQl.graphsDTOs.config_query_marketplace;

import github.ricemonger.marketplace.graphQl.graphsDTOs.config_query_marketplace.marketplace.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marketplace {

    private Tags tags;

    private TagGroups tagGroups;

    private Types types;
}
