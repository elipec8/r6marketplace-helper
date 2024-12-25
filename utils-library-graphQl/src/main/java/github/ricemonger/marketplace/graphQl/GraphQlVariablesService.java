package github.ricemonger.marketplace.graphQl;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GraphQlVariablesService {

    public static final int MAX_LIMIT = 500; // Ubisoft's GraphQL API limit - 500

    private final GraphQlCommonValuesService graphQlCommonValuesService;

    public Map<String, Object> getFetchOneItemVariables(String itemId) {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "itemId", itemId
        );
    }

    public Map<String, Object> getCreateSellOrderVariables(String itemId, int price) {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "tradeItems", List.of(
                        Map.of(
                                "itemId", itemId,
                                "quantity", 1)),
                "paymentOptions", List.of(
                        Map.of(
                                "paymentItemId", graphQlCommonValuesService.getPaymentItemId(),
                                "price", price))
        );
    }

    public Map<String, Object> getUpdateSellOrderVariables(String tradeId, int price) {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "tradeId", tradeId,
                "paymentOptions", List.of(
                        Map.of(
                                "paymentItemId", graphQlCommonValuesService.getPaymentItemId(),
                                "price", price))
        );
    }

    public Map<String, Object> getCreateBuyOrderVariables(String itemId, int price) {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "tradeItems", List.of(
                        Map.of(
                                "itemId", itemId,
                                "quantity", 1)),
                "paymentProposal", Map.of(
                        "paymentItemId", graphQlCommonValuesService.getPaymentItemId(),
                        "price", price)
        );
    }

    public Map<String, Object> getUpdateBuyOrderVariables(String tradeId, int price) {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "tradeId", tradeId,
                "paymentProposal", Map.of(
                        "paymentItemId", graphQlCommonValuesService.getPaymentItemId(),
                        "price", price)
        );
    }

    public Map<String, Object> getCancelOrderVariables(String tradeId) {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "tradeId", tradeId
        );
    }

    public Map<String, Object> getFetchOrdersVariables(int offset) {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "limit", MAX_LIMIT,
                "offset", offset
        );
    }

    public Map<String, Object> getFetchOrdersVariables(int offset, int limit) {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "limit", limit,
                "offset", offset
        );
    }

    public Map<String, Object> getFetchCreditAmountVariables() {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "itemId", graphQlCommonValuesService.getPaymentItemId()
        );
    }

    public Map<String, Object> getFetchConfigVariables() {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId());
    }

    public Map<String, Object> getFetchLockedItemsVariables() {
        return Map.of(
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId());
    }

    public Map<String, Object> getFetchItemsVariables(int offset) {
        return Map.of(
                "withOwnership", false,
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "limit", MAX_LIMIT,
                "offset", offset,
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", graphQlCommonValuesService.getPaymentItemId()));
    }

    public Map<String, Object> getFetchItemsUbiSaleStats(int offset) {
        return Map.of(
                "withOwnership", false,
                "spaceId", graphQlCommonValuesService.getUbiGameSpaceId(),
                "limit", MAX_LIMIT,
                "offset", offset,
                "paymentItemId", graphQlCommonValuesService.getPaymentItemId(),
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", graphQlCommonValuesService.getPaymentItemId()));
    }
}
