package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.databases.redis.services.RedisService;
import github.ricemonger.marketplace.graphQl.mappers.*;
import github.ricemonger.utils.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GraphQlClientService {

    private final GraphQlClientFactory graphQlClientFactory;

    private final CommonQueryItemsMapper commonQueryItemsMapper;

    private final ConfigQueryMarketplaceMapper configQueryMarketplaceMapper;

    private final ConfigQueryResolvedTransactionPeriodMapper configQueryResolvedTransactionPeriodMapper;

    private final ConfigQueryTradeMapper configQueryTradeMapper;

    private final PersonalQueryCreditAmountMapper personalQueryCreditAmountMapper;

    private final PersonalQueryCurrentOrdersMapper personalQueryCurrentOrdersMapper;

    private final PersonalQueryFinishedOrdersMapper personalQueryFinishedOrdersMapper;

    private final PersonalQueryLockedItemsMapper personalQueryLockedItemsMapper;

    private final PersonalQueryOneItemMapper personalQueryOneItemMapper;

    private final PersonalQueryOwnedItemsMapper personalQueryOwnedItemsMapper;

    public Collection<Item> fetchAllItemStats() {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.common_query_items.MarketableItems marketableItems;
        List<github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.Node> nodes = new ArrayList<>();
        int offset = 0;

        do {
            marketableItems = client
                    .documentName(GraphQlClientServiceStatics.QUERY_ITEMS_STATS_DOCUMENT_NAME)
                    .variables(GraphQlClientServiceStatics.getFetchItemsVariables(offset))
                    .retrieve("game.marketableItems")
                    .toEntity(github.ricemonger.marketplace.graphQl.dtos.common_query_items.MarketableItems.class)
                    .block();

            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlClientServiceStatics.MAX_LIMIT;
        }
        while (marketableItems.getTotalCount() == GraphQlClientServiceStatics.MAX_LIMIT);

        return commonQueryItemsMapper.mapItems(nodes);
    }

    public Collection<Tag> fetchAllTags() {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace marketplace = client
                .documentName(GraphQlClientServiceStatics.QUERY_MARKETPLACE_CONFIG_DOCUMENT_NAME)
                .retrieve("game.marketplace")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace.class)
                .block();

        return configQueryMarketplaceMapper.mapTags(marketplace);
    }

    public void checkItemTypes() {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace marketplace = client
                .documentName(GraphQlClientServiceStatics.QUERY_MARKETPLACE_CONFIG_DOCUMENT_NAME)
                .retrieve("game.marketplace")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace.class)
                .block();

        configQueryMarketplaceMapper.checkItemTypes(marketplace);
    }

    public ConfigResolvedTransactionPeriod fetchConfigResolvedTransactionPeriod() {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.config_query_resolved_transaction_period.TradeLimitations tradeLimitations = client
                .documentName(GraphQlClientServiceStatics.QUERY_RESOLVED_TRANSACTION_PERIOD_CONFIG_DOCUMENT_NAME)
                .retrieve("game.viewer.meta.tradeLimitations")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.config_query_resolved_transaction_period.TradeLimitations.class)
                .block();

        return configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(tradeLimitations);
    }

    public ConfigTrades fetchConfigTrades() {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.config_query_trade.TradesConfig tradesConfig = client
                .documentName(GraphQlClientServiceStatics.QUERY_TRADE_CONFIG_DOCUMENT_NAME)
                .retrieve("game.tradesConfig")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.config_query_trade.TradesConfig.class)
                .block();

        return configQueryTradeMapper.mapConfigTrades(tradesConfig);
    }

    public int fetchCreditAmountForUser(AuthorizationDTO authorizationDTO) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        github.ricemonger.marketplace.graphQl.dtos.personal_query_credits_amount.Meta meta = client
                .documentName(GraphQlClientServiceStatics.QUERY_CREDITS_AMOUNT_DOCUMENT_NAME)
                .retrieve("game.viewer.meta.secondaryStoreItem.meta")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_credits_amount.Meta.class)
                .block();

        return personalQueryCreditAmountMapper.mapCreditAmount(meta);
    }

    public Collection<Trade> fetchCurrentOrdersForUser(AuthorizationDTO authorizationDTO) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.Trades trades;
        List<github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.trades.Nodes> nodes = new ArrayList<>();

        trades = client
                .documentName(GraphQlClientServiceStatics.QUERY_CURRENT_ORDERS_DOCUMENT_NAME)
                .variables(GraphQlClientServiceStatics.getFetchItemsVariables(0))
                .retrieve("game.viewer.meta.trades")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.Trades.class)
                .block();

        nodes.addAll(trades.getNodes());

        return personalQueryCurrentOrdersMapper.mapCurrentOrders(trades);
    }

    public Collection<Trade> fetchFinishedOrdersForUser(AuthorizationDTO authorizationDTO) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.Trades trades;
        List<github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.Nodes> nodes = new ArrayList<>();
        int offset = 0;
        int totalCount = 0;

        do {
            trades = client
                    .documentName(GraphQlClientServiceStatics.QUERY_FINISHED_ORDERS_DOCUMENT_NAME)
                    .variables(GraphQlClientServiceStatics.getFetchItemsVariables(offset))
                    .retrieve("game.viewer.meta.trades")
                    .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.Trades.class)
                    .block();

            nodes.addAll(trades.getNodes());

            totalCount = trades.getNodes().size();

            offset += GraphQlClientServiceStatics.MAX_LIMIT;
        }
        while (totalCount == GraphQlClientServiceStatics.MAX_LIMIT);

        return personalQueryFinishedOrdersMapper.mapFinishedOrders(trades);
    }

    public Collection<LockedItem> fetchLockedItemsForUser(AuthorizationDTO authorizationDTO) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.TradeLimitations tradeLimitations;

        tradeLimitations = client
                .documentName(GraphQlClientServiceStatics.QUERY_LOCKED_ITEMS_DOCUMENT_NAME)
                .retrieve("game.viewer.meta.tradeLimitations")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.TradeLimitations.class)
                .block();

        return personalQueryLockedItemsMapper.mapLockedItems(tradeLimitations);
    }

    public Item fetchOneItem(AuthorizationDTO authorizationDTO, String itemId) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.Game game = client
                .documentName(GraphQlClientServiceStatics.QUERY_ONE_ITEM_STATS_DOCUMENT_NAME)
                .variables(GraphQlClientServiceStatics.getFetchOneItemVariables(itemId))
                .retrieve("game")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.Game.class)
                .block();

        return personalQueryOneItemMapper.mapItem(game);
    }


    public Collection<String> fetchAllOwnedItemsIdsForUser(AuthorizationDTO authorizationDTO) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.MarketableItems marketableItems;
        List<github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems.Node> nodes = new ArrayList<>();
        int offset = 0;

        do {
            marketableItems = client
                    .documentName(GraphQlClientServiceStatics.QUERY_OWNED_ITEMS_DOCUMENT_NAME)
                    .variables(GraphQlClientServiceStatics.getFetchItemsVariables(offset))
                    .retrieve("game.viewer.meta.marketableItems")
                    .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.MarketableItems.class)
                    .block();

            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlClientServiceStatics.MAX_LIMIT;
        }
        while (marketableItems.getTotalCount() == GraphQlClientServiceStatics.MAX_LIMIT);

        return personalQueryOwnedItemsMapper.mapOwnedItems(nodes);
    }

    public void createBuyOrderForUser(AuthorizationDTO authorizationDTO, String itemId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlClientServiceStatics.MUTATION_ORDER_BUY_CREATE_DOCUMENT_NAME)
                .variables(GraphQlClientServiceStatics.getCreateUpdateOrderVariables(itemId, price))
                .execute();
    }

    public void updateBuyOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlClientServiceStatics.MUTATION_ORDER_BUY_UPDATE_DOCUMENT_NAME)
                .variables(GraphQlClientServiceStatics.getCreateUpdateOrderVariables(tradeId, price))
                .execute();
    }

    public void createSellOrderForUser(AuthorizationDTO authorizationDTO, String itemId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlClientServiceStatics.MUTATION_ORDER_SELL_CREATE_DOCUMENT_NAME)
                .variables(GraphQlClientServiceStatics.getCreateUpdateOrderVariables(itemId, price))
                .execute();
    }

    public void updateSellOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlClientServiceStatics.MUTATION_ORDER_SELL_UPDATE_DOCUMENT_NAME)
                .variables(GraphQlClientServiceStatics.getCreateUpdateOrderVariables(tradeId, price))
                .execute();
    }

    public void cancelOrderForUser(AuthorizationDTO authorizationDTO, String tradeId) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlClientServiceStatics.MUTATION_ORDER_CANCEL_DOCUMENT_NAME)
                .variables(GraphQlClientServiceStatics.getCancelOrderVariables(tradeId))
                .execute();
    }
}
