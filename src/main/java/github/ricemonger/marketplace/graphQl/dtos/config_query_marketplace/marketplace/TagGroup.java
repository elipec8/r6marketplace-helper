package github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagGroup {
    private List<String> values;

    private String displayName;
}
