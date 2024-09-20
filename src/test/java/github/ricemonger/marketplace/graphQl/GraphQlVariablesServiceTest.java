package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.services.CommonValuesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GraphQlVariablesServiceTest {

    @MockBean
    private CommonValuesService commonValuesService;

    @Autowired
    private GraphQlVariablesService graphQlVariablesService;

    @Test
    public void getFetchOneItemVariables_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";
        String itemId = "itemId";

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);

        Map<String, Object> result = graphQlVariablesService.getFetchOneItemVariables(itemId);

        assertTrue(mapContainsEntries(result, Map.entry("spaceId", mainUserSpaceId), Map.entry("itemId", itemId)));
    }

    @Test
    public void getCreateUpdateOrderVariables_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";
        String itemId = "itemId";
        int price = 100;

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);
        when(commonValuesService.getPaymentItemId()).thenReturn("paymentItemId");

        Map<String, Object> result = graphQlVariablesService.getCreateUpdateOrderVariables(itemId, price);

        assertTrue(mapContainsEntries(result, Map.entry("spaceId", mainUserSpaceId), Map.entry("tradeItems", List.of(Map.of("itemId", itemId,
                "quantity", 1))), Map.entry("paymentOptions", List.of(Map.of("paymentItemId", "paymentItemId", "price", price)))));
    }

    @Test
    public void getCancelOrderVariables_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";
        String tradeId = "tradeId";

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);

        Map<String, Object> result = graphQlVariablesService.getCancelOrderVariables(tradeId);

        assertTrue(mapContainsEntries(result, Map.entry("spaceId", mainUserSpaceId), Map.entry("tradeId", tradeId)));
    }

    @Test
    public void getFetchOrdersVariables_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";
        int offset = 0;

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);

        Map<String, Object> result = graphQlVariablesService.getFetchOrdersVariables(offset);

        assertTrue(mapContainsEntries(result, Map.entry("spaceId", mainUserSpaceId), Map.entry("limit", 500), Map.entry("offset", offset)));
    }

    @Test
    public void getFetchCreditAmountVariables_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";
        String paymentItemId = "paymentItemId";

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);
        when(commonValuesService.getPaymentItemId()).thenReturn(paymentItemId);

        Map<String, Object> result = graphQlVariablesService.getFetchCreditAmountVariables();

        assertTrue(mapContainsEntries(result, Map.entry("spaceId", mainUserSpaceId), Map.entry("itemId", paymentItemId)));
    }

    @Test
    public void getFetchConfigVariables_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);

        Map<String, Object> result = graphQlVariablesService.getFetchConfigVariables();

        assertTrue(mapContainsEntries(result, Map.entry("spaceId", mainUserSpaceId)));
    }

    @Test
    public void getFetchLockedItemsVariables_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);

        Map<String, Object> result = graphQlVariablesService.getFetchLockedItemsVariables();

        assertTrue(mapContainsEntries(result, Map.entry("spaceId", mainUserSpaceId)));
    }

    @Test
    public void getFetchItemsVariables_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";
        String paymentItemId = "paymentItemId";
        int offset = 0;

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);
        when(commonValuesService.getPaymentItemId()).thenReturn("paymentItemId");

        Map<String, Object> result = graphQlVariablesService.getFetchItemsVariables(offset);

        assertTrue(mapContainsEntries(result, Map.entry("spaceId", mainUserSpaceId), Map.entry("offset", offset)));

        assertTrue(mapContainsEntries((Map) result.get("sortBy"), Map.entry("paymentItemId", paymentItemId)));
    }

    @Test
    public void getFetchItemsUbiSaleStats_should_have_provided_variables() {
        String mainUserSpaceId = "mainUserSpaceId";
        String paymentItemId = "paymentItemId";
        int offset = 0;

        when(commonValuesService.getUbiGameSpaceId()).thenReturn(mainUserSpaceId);
        when(commonValuesService.getPaymentItemId()).thenReturn("paymentItemId");

        Map<String, Object> result = graphQlVariablesService.getFetchItemsUbiSaleStats(offset);

        assertTrue(mapContainsEntries(result, Map.entry("withOwnership", false), Map.entry("spaceId", mainUserSpaceId), Map.entry("offset", offset)));

        assertTrue(mapContainsEntries((Map) result.get("sortBy"), Map.entry("field", "ACTIVE_COUNT"), Map.entry("orderType", "Sell"),
                Map.entry("direction", "DESC"), Map.entry("paymentItemId", paymentItemId)));
    }

    @SafeVarargs
    private <K, V> boolean mapContainsEntries(Map<K, V> map, Map.Entry<K, V>... entries) {
        for (Map.Entry<K, V> entry : entries) {
            if (!map.containsKey(entry.getKey()) || !map.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
