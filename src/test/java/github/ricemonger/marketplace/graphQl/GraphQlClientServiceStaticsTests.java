package github.ricemonger.marketplace.graphQl;

import org.junit.jupiter.api.Test;

import java.util.Map;

public class GraphQlClientServiceStaticsTests {

    @Test
    public void variablesShouldHaveRightTypes(){
        Map<String, Object> variables = GraphQlClientServiceStatics.getFetchItemsVariables();

        assert(variables.get("spaceId") instanceof String);

        assert(variables.get("withOwnership") instanceof Boolean);

        assert(variables.get("limit") instanceof Integer);
        assert(variables.get("offset") instanceof Integer);

        assert(variables.get("sortBy") instanceof Map);
        Map<String, Object> sortBy = (Map<String, Object>) variables.get("sortBy");

        assert(sortBy.get("field") instanceof String);
        assert(sortBy.get("orderType") instanceof String);
        assert(sortBy.get("direction") instanceof String);
        assert(sortBy.get("paymentItemId") instanceof String);


        assert(variables.get("filterBy") instanceof Map);
        Map<String, Object> filterBy = (Map<String, Object>) variables.get("filterBy");

        assert(filterBy.get("types") instanceof java.util.List);
        assert(filterBy.get("tags") instanceof java.util.List);
    }
}
