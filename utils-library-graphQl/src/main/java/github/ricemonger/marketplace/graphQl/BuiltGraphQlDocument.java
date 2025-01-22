package github.ricemonger.marketplace.graphQl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuiltGraphQlDocument {
    private String document;
    private Map<String, Object> variables;
    private Map<String, String> aliasesToFields;
}
