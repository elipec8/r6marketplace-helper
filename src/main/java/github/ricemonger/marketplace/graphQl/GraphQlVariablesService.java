package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.databases.redis.services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GraphQlVariablesService {

    public static final int MAX_LIMIT = 500; // Ubisoft's GraphQL API limit - 500

    private final RedisService redisService;

    public Map<String, Object> getFetchOneItemVariables(String itemId) {
        return Map.of(
                "spaceId", redisService.getGameSpaceId(),
                "itemId", itemId
        );
    }

    public Map<String, Object> getCreateUpdateOrderVariables(String itemId, int price) {
        return Map.of(
                "spaceId", redisService.getGameSpaceId(),
                "tradeItems", List.of(
                        Map.of(
                                "itemId", itemId,
                                "quantity", 1)),
                "paymentOptions", List.of(
                        Map.of(
                                "paymentItemId", redisService.getPaymentItemId(),
                                "price", price))
        );
    }

    public Map<String, Object> getCancelOrderVariables(String tradeId) {
        return Map.of(
                "spaceId", redisService.getGameSpaceId(),
                "tradeId", tradeId
        );
    }

    public Map<String, Object> getFetchOrdersVariables(int offset) {
        return Map.of(
                "spaceId", redisService.getGameSpaceId(),
                "limit", MAX_LIMIT,
                "offset", offset
        );
    }

    public Map<String, Object> getFetchCreditAmountVariables() {
        return Map.of(
                "spaceId", redisService.getGameSpaceId(),
                "itemId", redisService.getPaymentItemId()
        );
    }

    public Map<String, Object> getFetchConfigVariables() {
        return Map.of(
                "spaceId", redisService.getGameSpaceId());
    }

    public Map<String,Object> getFetchLockedItemsVariables(){
        return Map.of(
                "spaceId", redisService.getGameSpaceId());
    }

    public Map<String, Object> getFetchItemsVariables(int offset) {
        return Map.of(
                "withOwnership", false,
                "spaceId", redisService.getGameSpaceId(),
                "limit", MAX_LIMIT,
                "offset", offset,
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", redisService.getPaymentItemId()));
    }
}
