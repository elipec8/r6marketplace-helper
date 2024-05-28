package github.ricemonger.marketplace.graphQl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GraphQlClientServiceStatics {

    public final static String QUERY_ITEMS_STATS_DOCUMENT_NAME = "common_query_items";

    public final static String QUERY_MARKETPLACE_CONFIG_DOCUMENT_NAME = "config_query_marketplace";

    public final static String QUERY_RESOLVED_TRANSACTION_PERIOD_CONFIG_DOCUMENT_NAME = "config_query_resolved_transaction_period";

    public final static String QUERY_TRADE_CONFIG_DOCUMENT_NAME = "config_query_trade";

    public final static String MUTATION_ORDER_BUY_CREATE_DOCUMENT_NAME = "personal_mutation_order_buy_create";

    public final static String MUTATION_ORDER_BUY_UPDATE_DOCUMENT_NAME = "personal_mutation_order_buy_update";

    public final static String MUTATION_ORDER_CANCEL_DOCUMENT_NAME = "personal_mutation_order_cancel";

    public final static String MUTATION_ORDER_SELL_CREATE_DOCUMENT_NAME = "personal_mutation_order_sell_create";

    public final static String MUTATION_ORDER_SELL_UPDATE_DOCUMENT_NAME = "personal_mutation_order_sell_update";

    public final static String QUERY_CREDITS_AMOUNT_DOCUMENT_NAME = "personal_query_credits_amount";

    public final static String QUERY_CURRENT_ORDERS_DOCUMENT_NAME = "personal_query_current_orders";

    public final static String QUERY_FINISHED_ORDERS_DOCUMENT_NAME = "personal_query_finished_orders";

    public final static String QUERY_LOCKED_ITEMS_DOCUMENT_NAME = "personal_query_locked_items";

    public final static String QUERY_ONE_ITEM_STATS_DOCUMENT_NAME = "personal_query_one_item";

    public final static String QUERY_OWNED_ITEMS_DOCUMENT_NAME = "personal_query_owned_items";

    public static final int MAX_LIMIT = 500; // Ubisoft's GraphQL API limit - 500

    public static final String SPACE_ID = "0d2ae42d-4c27-4cb7-af6c-2099062302bb";

    public static final String PAYMENT_ITEM_ID = "9ef71262-515b-46e8-b9a8-b6b6ad456c67";

    private final static Map<String, Object> defaultFetchItemsVariables;

    private final static Map<String, Object> defaultFetchConfigVariables;

    private final static Map<String, Object> defaultFetchOneItemVariables;

    private final static Map<String, Object> defaultCreateUpdateOrderVariables;

    private final static Map<String, Object> defaultCancelOrderVariables;

    private final static Map<String, Object> defaultFetchOrdersVariables;

    private final static Map<String, Object> defaultFetchCreditAmountVariables;

    static {
        defaultFetchItemsVariables = Map.of(//Requires offset
                "withOwnership", false, //Important to be non-String Boolean
                "spaceId", SPACE_ID,
                "limit", MAX_LIMIT,//Important to be non-String Integer
                "offset", 0,//Important to be non-String Integer
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", PAYMENT_ITEM_ID));

        defaultFetchConfigVariables = Map.of(
                "spaceId", SPACE_ID
        );

        defaultFetchOneItemVariables = Map.of( //Requires itemId
                "spaceId", SPACE_ID,
                "itemId", ""
        );

        defaultCreateUpdateOrderVariables = Map.of( // Requires tradeItems.itemId, paymentOptions.price
                "spaceId", SPACE_ID,
                "tradeItems", List.of(
                Map.of(
                        "itemId", "",
                        "quantity", 1)),
                "paymentOptions", List.of(
                        Map.of(
                        "paymentItemId", "9ef71262-515b-46e8-b9a8-b6b6ad456c67",
                        "price", 0))
        );

        defaultCancelOrderVariables = Map.of( //Requires tradeId
                "spaceId", SPACE_ID,
                "tradeId", ""
        );

        defaultFetchOrdersVariables = Map.of(
                "spaceId", SPACE_ID,
                "offset", 0,
                "limit", MAX_LIMIT
        );

        defaultFetchCreditAmountVariables = Map.of(
                "spaceId", SPACE_ID,
                "itemId", PAYMENT_ITEM_ID
        );
    }

    public static Map<String, Object> getFetchItemsVariables(int offset) {
        HashMap<String, Object> fetchItemsVariables = new HashMap<>(defaultFetchItemsVariables);
        fetchItemsVariables.put("offset", offset);
        return fetchItemsVariables;
    }

    public static Map<String, Object> getFetchOneItemVariables(String itemId) {
        HashMap<String, Object> fetchOneItemVariables = new HashMap<>(defaultFetchOneItemVariables);
        fetchOneItemVariables.put("itemId", itemId);
        return fetchOneItemVariables;
    }

    public static Map<String, Object> getCreateUpdateOrderVariables(String itemId, int price) {
        HashMap<String, Object> createUpdateOrderVariables = new HashMap<>(defaultCreateUpdateOrderVariables);

        List<Map<String, Object>> tradeItems = (List<Map<String, Object>>) createUpdateOrderVariables.get("tradeItems");
        tradeItems.get(0).put("itemId", itemId);
        List<Map<String, Object>> paymentOptions = (List<Map<String, Object>>) createUpdateOrderVariables.get("paymentOptions");
        paymentOptions.get(0).put("price", price);

        return createUpdateOrderVariables;
    }

    public static Map<String, Object> getCancelOrderVariables(String tradeId) {
        HashMap<String, Object> cancelOrderVariables = new HashMap<>(defaultCancelOrderVariables);
        cancelOrderVariables.put("tradeId", tradeId);
        return cancelOrderVariables;
    }

    public static Map<String, Object> getFetchOrdersVariables(int offset) {
        HashMap<String, Object> fetchOrdersVariables = new HashMap<>(defaultFetchOrdersVariables);
        fetchOrdersVariables.put("offset", offset);
        return fetchOrdersVariables;
    }

    public static Map<String, Object> getFetchCreditAmountVariables() {
        return defaultFetchCreditAmountVariables;
    }

    public static Map<String, Object> getFetchConfigVariables() {
        return defaultFetchConfigVariables;
    }
}
