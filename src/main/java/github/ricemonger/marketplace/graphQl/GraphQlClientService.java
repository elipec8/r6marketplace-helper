package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.graphQl.mappers.*;
import github.ricemonger.utils.dtos.*;
import github.ricemonger.utils.exceptions.server.*;
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

    private final GraphQlVariablesService graphQlVariablesService;

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

    public Collection<Item> fetchAllItemStats() throws GraphQlCommonItemMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.common_query_items.MarketableItems marketableItems;
        List<github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.Node> nodes = new ArrayList<>();
        int offset = 0;

        do {
            marketableItems = client
                    .documentName(GraphQlDocuments.QUERY_ITEMS_STATS_DOCUMENT_NAME)
                    .variables(graphQlVariablesService.getFetchItemsVariables(offset))
                    .retrieve("game.marketableItems")
                    .toEntity(github.ricemonger.marketplace.graphQl.dtos.common_query_items.MarketableItems.class)
                    .block();

            if (marketableItems == null || marketableItems.getNodes() == null || marketableItems.getTotalCount() == null) {
                throw new GraphQlCommonItemMappingException("MarketableItems or it's field is null");
            }

            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlVariablesService.MAX_LIMIT;
        }
        while (marketableItems.getTotalCount() == GraphQlVariablesService.MAX_LIMIT);

        return commonQueryItemsMapper.mapItems(nodes);
    }

    public Collection<Tag> fetchAllTags() throws GraphQlConfigMarketplaceMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace marketplace = client
                .documentName(GraphQlDocuments.QUERY_MARKETPLACE_CONFIG_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchConfigVariables())
                .retrieve("game.marketplace")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace.class)
                .block();

        return configQueryMarketplaceMapper.mapTags(marketplace);
    }

    public void checkItemTypes() throws GraphQlConfigMarketplaceMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace marketplace = client
                .documentName(GraphQlDocuments.QUERY_MARKETPLACE_CONFIG_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchConfigVariables())
                .retrieve("game.marketplace")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace.class)
                .block();

        configQueryMarketplaceMapper.checkItemTypes(marketplace);
    }

    public ConfigResolvedTransactionPeriod fetchConfigResolvedTransactionPeriod() throws GraphQlConfigResolvedTransactionPeriodMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();

        github.ricemonger.marketplace.graphQl.dtos.config_query_resolved_transaction_period.TradesLimitations tradesLimitations = client
                .documentName(GraphQlDocuments.QUERY_RESOLVED_TRANSACTION_PERIOD_CONFIG_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchConfigVariables())
                .retrieve("game.viewer.meta.tradesLimitations")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.config_query_resolved_transaction_period.TradesLimitations.class)
                .block();

        return configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(tradesLimitations);
    }

    public ConfigTrades fetchConfigTrades() throws GraphQlConfigTradeMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.dtos.config_query_trade.TradesConfig tradesConfig = client
                .documentName(GraphQlDocuments.QUERY_TRADE_CONFIG_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchConfigVariables())
                .retrieve("game.tradesConfig")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.config_query_trade.TradesConfig.class)
                .block();

        return configQueryTradeMapper.mapConfigTrades(tradesConfig);
    }

    public int fetchCreditAmountForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalCreditAmountMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        github.ricemonger.marketplace.graphQl.dtos.personal_query_credits_amount.Meta meta = client
                .documentName(GraphQlDocuments.QUERY_CREDITS_AMOUNT_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchCreditAmountVariables())
                .retrieve("game.viewer.meta.secondaryStoreItem.meta")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_credits_amount.Meta.class)
                .block();

        return personalQueryCreditAmountMapper.mapCreditAmount(meta);
    }

    public Collection<Trade> fetchCurrentOrdersForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalCurrentOrderMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.Trades trades;

        trades = client
                .documentName(GraphQlDocuments.QUERY_CURRENT_ORDERS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchOrdersVariables(0))
                .retrieve("game.viewer.meta.trades")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.Trades.class)
                .block();

        return personalQueryCurrentOrdersMapper.mapCurrentOrders(trades);
    }

    public Collection<Trade> fetchFinishedOrdersForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalFinishedOrdersMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.Trades trades;
        int offset = 0;
        int totalCount;

        do {
            trades = client
                    .documentName(GraphQlDocuments.QUERY_FINISHED_ORDERS_DOCUMENT_NAME)
                    .variables(graphQlVariablesService.getFetchOrdersVariables(offset))
                    .retrieve("game.viewer.meta.trades")
                    .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.Trades.class)
                    .block();

            if (trades == null || trades.getNodes() == null) {
                throw new GraphQlPersonalFinishedOrdersMappingException("Trades or it's field is null");
            }

            totalCount = trades.getNodes().size();

            offset += GraphQlVariablesService.MAX_LIMIT;
        }
        while (totalCount == GraphQlVariablesService.MAX_LIMIT);

        return personalQueryFinishedOrdersMapper.mapFinishedOrders(trades);
    }

    public Collection<LockedItem> fetchLockedItemsForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalLockedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.TradeLimitations tradeLimitations;

        tradeLimitations = client
                .documentName(GraphQlDocuments.QUERY_LOCKED_ITEMS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchLockedItemsVariables())
                .retrieve("game.viewer.meta.tradeLimitations")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.TradeLimitations.class)
                .block();

        return personalQueryLockedItemsMapper.mapLockedItems(tradeLimitations);
    }

    public PersonalItem fetchOneItem(AuthorizationDTO authorizationDTO, String itemId) throws GraphQlPersonalOneItemMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.Game game = client
                .documentName(GraphQlDocuments.QUERY_ONE_ITEM_STATS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchOneItemVariables(itemId))
                .retrieve("game")
                .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.Game.class)
                .block();

        return personalQueryOneItemMapper.mapItem(game);
    }


    public Collection<String> fetchAllOwnedItemsIdsForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalOwnedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.MarketableItems marketableItems;
        List<github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems.Node> nodes = new ArrayList<>();
        int offset = 0;

        do {
            marketableItems = client
                    .documentName(GraphQlDocuments.QUERY_OWNED_ITEMS_DOCUMENT_NAME)
                    .variables(graphQlVariablesService.getFetchItemsVariables(offset))
                    .retrieve("game.viewer.meta.marketableItems")
                    .toEntity(github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.MarketableItems.class)
                    .block();

            if (marketableItems == null || marketableItems.getNodes() == null || marketableItems.getTotalCount() == null) {
                throw new GraphQlPersonalOwnedItemsMappingException("MarketableItems or it's field is null");
            }

            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlVariablesService.MAX_LIMIT;
        }
        while (marketableItems.getTotalCount() == GraphQlVariablesService.MAX_LIMIT);

        return personalQueryOwnedItemsMapper.mapOwnedItems(nodes);
    }

    public void createBuyOrderForUser(AuthorizationDTO authorizationDTO, String itemId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_BUY_CREATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getCreateUpdateOrderVariables(itemId, price))
                .execute();
    }

    public void updateBuyOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_BUY_UPDATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getCreateUpdateOrderVariables(tradeId, price))
                .execute();
    }

    public void createSellOrderForUser(AuthorizationDTO authorizationDTO, String itemId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_SELL_CREATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getCreateUpdateOrderVariables(itemId, price))
                .execute();
    }

    public void updateSellOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_SELL_UPDATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getCreateUpdateOrderVariables(tradeId, price))
                .execute();
    }

    public void cancelOrderForUser(AuthorizationDTO authorizationDTO, String tradeId) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_CANCEL_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getCancelOrderVariables(tradeId))
                .execute();
    }
}
