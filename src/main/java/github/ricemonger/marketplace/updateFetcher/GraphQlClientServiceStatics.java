package github.ricemonger.marketplace.updateFetcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class GraphQlClientServiceStatics {

    public final static String FETCH_ITEMS_STATS_DOCUMENT_NAME = "fetchItemsStats";

    public static final int MAX_LIMIT = 500; // Ubisoft's GraphQL API limit - 500

    private final static Map<String, Object> defaultUpdateItemsVariables;

    static{
        HashMap<String, Object> map = new HashMap<>();

        map.put("withOwnership", true); //Important to be non-String Boolean
        map.put("spaceId", "0d2ae42d-4c27-4cb7-af6c-2099062302bb");
        map.put("limit", MAX_LIMIT);//Important to be non-String Integer
        map.put("offset", 0);//Important to be non-String Integer

        map.put("filterBy", Map.of(
                "types", Collections.emptyList(),
                "tags", Collections.emptyList()));

        map.put("sortBy", Map.of(
                "field", "ACTIVE_COUNT",
                "orderType", "Sell",
                "direction", "DESC",
                "paymentItemId", "9ef71262-515b-46e8-b9a8-b6b6ad456c67"));

        defaultUpdateItemsVariables = Collections.unmodifiableMap(map);
    }

    public static Map<String, Object> getDefaultUpdateItemsVariables() {
        return new HashMap<>(defaultUpdateItemsVariables);
    }
}
