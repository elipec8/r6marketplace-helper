package github.ricemonger.marketplace.graphQl.DTOs.config_query_marketplace;

import github.ricemonger.marketplace.graphQl.DTOs.config_query_marketplace.marketplace.TagGroup;
import github.ricemonger.marketplace.graphQl.DTOs.config_query_marketplace.marketplace.Tags;
import github.ricemonger.marketplace.graphQl.DTOs.config_query_marketplace.marketplace.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marketplace {
    private List<Tags> tags;
    private List<TagGroup> tagGroups;
    private List<Type> types;
}
