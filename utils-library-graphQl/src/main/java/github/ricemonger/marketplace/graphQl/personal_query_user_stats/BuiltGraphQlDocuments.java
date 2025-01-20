package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuiltGraphQlDocuments {
    private String document;
    private Map<String, Object> variables;
    private Map<String, String> aliasesToFields;
}
