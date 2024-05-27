package github.ricemonger.marketplace.graphQl.graphsDTOs.config_query_marketplace.marketplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagGroups {
    private List<String> values;

    private String displayName;
}
