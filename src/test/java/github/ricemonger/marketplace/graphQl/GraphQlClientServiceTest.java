package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.graphQl.mappers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraphQlClientServiceTest {
    @Autowired
    private GraphQlClientService graphQlClientService;
    @Autowired
    private GraphQlClientFactory graphQlClientFactory;
    @Autowired
    private GraphQlVariablesService graphQlVariablesService;
    @Autowired
    private CommonQueryItemsMapper commonQueryItemsMapper;
    @Autowired
    private ConfigQueryMarketplaceMapper configQueryMarketplaceMapper;
    @Autowired
    private ConfigQueryResolvedTransactionPeriodMapper configQueryResolvedTransactionPeriodMapper;
    @Autowired
    private ConfigQueryTradeMapper configQueryTradeMapper;
    @Autowired
    private PersonalQueryCreditAmountMapper personalQueryCreditAmountMapper;
    @Autowired
    private PersonalQueryCurrentOrdersMapper personalQueryCurrentOrdersMapper;
    @Autowired
    private PersonalQueryFinishedOrdersMapper personalQueryFinishedOrdersMapper;
    @Autowired
    private PersonalQueryLockedItemsMapper personalQueryLockedItemsMapper;
    @Autowired
    private PersonalQueryOneItemMapper personalQueryOneItemMapper;
    @Autowired
    private PersonalQueryOwnedItemsMapper personalQueryOwnedItemsMapper;

    @Test
    void fetchAllItemStats() {
    }

    @Test
    void fetchAllTags() {
    }

    @Test
    void checkItemTypes() {
    }

    @Test
    void fetchConfigResolvedTransactionPeriod() {
    }

    @Test
    void fetchConfigTrades() {
    }

    @Test
    void fetchCreditAmountForUser() {
    }

    @Test
    void fetchCurrentOrdersForUser() {
    }

    @Test
    void fetchFinishedOrdersForUser() {
    }

    @Test
    void fetchLockedItemsForUser() {
    }

    @Test
    void fetchOneItem() {
    }

    @Test
    void fetchAllOwnedItemsIdsForUser() {
    }

    @Test
    void createBuyOrderForUser() {
    }

    @Test
    void updateBuyOrderForUser() {
    }

    @Test
    void createSellOrderForUser() {
    }

    @Test
    void updateSellOrderForUser() {
    }

    @Test
    void cancelOrderForUser() {
    }
}