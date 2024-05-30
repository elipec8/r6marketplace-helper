package github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace;

import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marketplace {
    private List<Tags> tags;
    private List<TagGroups> tagGroups;
    private List<Types> types;
}
