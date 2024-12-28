package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.graphQl.DTOs.personal_query_finished_orders.Trades;
import github.ricemonger.marketplace.graphQl.mappers.*;
import github.ricemonger.marketplace.graphQl.personal_query_credits_amount.DTO.personal_query_credits_amount.Meta;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.DTOs.personal.UserTradesLimitations;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.*;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GraphQlClientService {

    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryCreditAmountMapper personalQueryCreditAmountMapper;

    private final PersonalQueryCurrentOrdersMapper personalQueryCurrentOrdersMapper;

    private final PersonalQueryFinishedOrdersMapper personalQueryFinishedOrdersMapper;

    private final PersonalQueryLockedItemsMapper personalQueryLockedItemsMapper;

    private final PersonalQueryOwnedItemsMapper personalQueryOwnedItemsMapper;

    public int fetchCreditAmountForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalCreditAmountMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        Meta meta = client
                .documentName(GraphQlDocuments.QUERY_CREDITS_AMOUNT_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchCreditAmountVariables())
                .retrieve("game.viewer.meta.secondaryStoreItem.meta")
                .toEntity(Meta.class)
                .block();

        return personalQueryCreditAmountMapper.mapCreditAmount(meta);
    }

    public List<UbiTrade> fetchCurrentOrdersForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalCurrentOrderMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.Trades trades;

        trades = client
                .documentName(GraphQlDocuments.QUERY_CURRENT_ORDERS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchOrdersVariables(0))
                .retrieve("game.viewer.meta.trades")
                .toEntity(github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.Trades.class)
                .block();

        return personalQueryCurrentOrdersMapper.mapCurrentOrders(trades);
    }

    public List<UbiTrade> fetchLastFinishedOrdersForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalFinishedOrdersMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        Trades trades;
        int offset = 0;
        int limit = 50;

        trades = client
                .documentName(GraphQlDocuments.QUERY_FINISHED_ORDERS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchOrdersVariables(offset, limit))
                .retrieve("game.viewer.meta.trades")
                .toEntity(Trades.class)
                .block();

        if (trades == null || trades.getNodes() == null) {
            throw new GraphQlPersonalFinishedOrdersMappingException("Trades or it's field is null");
        }

        return personalQueryFinishedOrdersMapper.mapFinishedOrders(trades);
    }

    public UserTradesLimitations fetchTradesLimitationsForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalLockedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        github.ricemonger.marketplace.graphQl.DTOs.personal_query_locked_items.TradesLimitations tradesLimitations;

        tradesLimitations = client
                .documentName(GraphQlDocuments.QUERY_LOCKED_ITEMS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchLockedItemsVariables())
                .retrieve("game.viewer.meta.tradesLimitations")
                .toEntity(github.ricemonger.marketplace.graphQl.DTOs.personal_query_locked_items.TradesLimitations.class)
                .block();

        return personalQueryLockedItemsMapper.mapTradesLimitationsForUser(tradesLimitations, authorizationDTO.getProfileId());
    }

    public List<String> fetchAllOwnedItemsIdsForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalOwnedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        github.ricemonger.marketplace.graphQl.DTOs.personal_query_owned_items.MarketableItems marketableItems;
        List<github.ricemonger.marketplace.graphQl.DTOs.personal_query_owned_items.marketableItems.Node> nodes = new ArrayList<>();
        int offset = 0;

        do {
            marketableItems = client
                    .documentName(GraphQlDocuments.QUERY_OWNED_ITEMS_DOCUMENT_NAME)
                    .variables(graphQlVariablesService.getFetchItemsVariables(offset))
                    .retrieve("game.viewer.meta.marketableItems")
                    .toEntity(github.ricemonger.marketplace.graphQl.DTOs.personal_query_owned_items.MarketableItems.class)
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
                .variables(graphQlVariablesService.getCreateBuyOrderVariables(itemId, price))
                .execute().block();
    }

    public void updateBuyOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_BUY_UPDATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getUpdateBuyOrderVariables(tradeId, price))
                .execute().block();
    }

    public void createSellOrderForUser(AuthorizationDTO authorizationDTO, String itemId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_SELL_CREATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getCreateSellOrderVariables(itemId, price))
                .execute().block();
    }

    public void updateSellOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_SELL_UPDATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getUpdateSellOrderVariables(tradeId, price))
                .execute().block();
    }

    public void cancelOrderForUser(AuthorizationDTO authorizationDTO, String tradeId) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_CANCEL_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getCancelOrderVariables(tradeId))
                .execute().block();
    }
}
