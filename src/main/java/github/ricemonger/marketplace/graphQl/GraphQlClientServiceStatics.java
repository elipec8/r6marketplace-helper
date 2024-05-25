package github.ricemonger.marketplace.graphQl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GraphQlClientServiceStatics {

    public final static String FETCH_CREDIT_AMOUNT_DOCUMENT_NAME = "fetchCreditAmount";

    public final static String CANCEL_ORDER_DOCUMENT_NAME = "cancelOrder";

    public final static String CREATE__BUY_ORDER_DOCUMENT_NAME = "createBuyOrder";

    public final static String CREATE__SELL_ORDER_DOCUMENT_NAME = "createSellOrder";

    public final static String FETCH_CREDITS_AMOUNT_DOCUMENT_NAME = "fetchCreditsAmount";

    public final static String FETCH_CURRENT_ORDERS_DOCUMENT_NAME = "fetchCurrentOrders";

    public final static String FETCH_FINISHED_ORDERS_DOCUMENT_NAME = "fetchFinishedOrders";

    public final static String FETCH_ITEMS_STATS_DOCUMENT_NAME = "fetchItemsStats";

    public final static String FETCH_LOCKED_ITEMS_STATS_DOCUMENT_NAME = "fetchLockedItems";

    public final static String FETCH_MARKETPLACE_CONFIG_DOCUMENT_NAME = "fetchMarketplaceConfig";

    public final static String FETCH_ONE_ITEM_STATS_DOCUMENT_NAME = "fetchOneItemStats";

    public final static String FETCH_OWNED_ITEMS_STATS_DOCUMENT_NAME = "fetchOwnedItemsStats";

    public final static String FETCH_TRADE_CONFIG_DOCUMENT_NAME = "fetchTradeConfig";

    public final static String UPDATE_BUY_ORDER_DOCUMENT_NAME = "updateBuyOrder";

    public final static String UPDATE_SELL_ORDER_DOCUMENT_NAME = "updateSellOrder";

    public static final int MAX_LIMIT = 500; // Ubisoft's GraphQL API limit - 500

    public static final String SPACE_ID = "0d2ae42d-4c27-4cb7-af6c-2099062302bb";

    public static final String PAYMENT_ITEM_ID = "9ef71262-515b-46e8-b9a8-b6b6ad456c67";

    private final static Map<String, Object> defaultFetchItemsVariables;

    private final static Map<String, Object> defaultFetchOneItemVariables;

    private final static Map<String, Object> defaultCreateUpdateOrderVariables;

    private final static Map<String, Object> defaultCancelOrderVariables;

    private final static Map<String, Object> defaultFetchOrdersVariables;

    private final static Map<String, Object> defaultFetchCreditAmountVariables;

    private final static Map<String, Object> defaultFetchConfigVariables;

    static {
        defaultFetchItemsVariables = Map.of(
                "withOwnership", false, //Important to be non-String Boolean
                "spaceId", SPACE_ID,
                "limit", MAX_LIMIT,//Important to be non-String Integer
                "offset", 0,//Important to be non-String Integer

                "filterBy", Map.of(
                        "types", Collections.emptyList(),
                        "tags", Collections.emptyList()), "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", PAYMENT_ITEM_ID));

        defaultFetchOneItemVariables = Map.of( //Requires itemId
                "spaceId", SPACE_ID,
                "itemId", "",
                "tradeId", "",
                "fetchTrade", false
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

        defaultFetchConfigVariables = Map.of(
                "spaceId", SPACE_ID
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
