package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.BuiltGraphQlDocument;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PersonalQueryUserStatsGraphQlDocumentBuilder {

    private final static String QUERY_PART_BEFORE_VARS =
            """
                    query GetUserStats(
                     $spaceId: String!,
                     $tradesLimit: Int!,
                     $tradesOffset: Int,
                     $paymentItemId: String!,
                     $ownedItemsSortBy: MarketableItemSort,
                     $ownedItemsLimit: Int!
                    """;

    private final static String QUERY_PART_AFTER_VARS_BEFORE_ADDITIONAL_QUERIES = """ 
            ) {
            game(spaceId: $spaceId) {
                viewer {
                    meta { 
            """;

    private final static String QUERY_PART_AFTER_ADDITIONAL_QUERIES = """ 
                            tradesLimitations {
                                ...TradesLimitationsFragment
                                __typename
                            }
            
                            secondaryStoreItem(itemId: $paymentItemId) {
                                meta {
                                    quantity
                                    __typename
                                }
                                __typename
                            }
            
                            currentTrades: trades(
                                                 limit: $tradesLimit
                                                 offset: $tradesOffset
                                                 filterBy: {states: [Created]}
                                                 sortBy: {field: LAST_MODIFIED_AT}
                                             ) {
                                                 nodes {
                                                     ...TradeFragment
                                                     __typename
                                                 }
                                                 __typename
                                             }
            
                            finishedTrades: trades(
                                                 limit: $tradesLimit
                                                 offset: $tradesOffset
                                                 filterBy: {states: [Succeeded, Failed]}
                                                 sortBy: {field: LAST_MODIFIED_AT}
                                             ) {
                                                 nodes {
                                                     ...TradeFragment
                                                     __typename
                                                 }
                                                 __typename
                                             }
            
                            __typename
                        }
                        __typename
                    }
                    __typename
                }
            }
            
            fragment TradesLimitationsFragment on UserGameTradesLimitations {
                buy {
                    resolvedTransactionCount
                    activeTransactionCount
                    __typename
                }
                sell {
                    resolvedTransactionCount
                    activeTransactionCount
                    resaleLocks {
                        itemId
                        expiresAt
                        __typename
                    }
                    __typename
                }
                __typename
            }
            
            fragment MarketableItemFragment on MarketableItem {
                item {
                    ...SecondaryStoreItemFragment
                    __typename
                }
                __typename
            }
            
            fragment TradeFragment on Trade {
                tradeId
                state
                category
                expiresAt
                lastModifiedAt
                tradeItems {
                    item {
                        itemId
                        name
                        __typename
                    }
                    __typename
                }
                payment {
                    price
                    transactionFee
                    __typename
                }
                paymentOptions {
                    price
                    __typename
                }
                paymentProposal {
                    price
                    __typename
                }
                __typename
            }
            
            fragment SecondaryStoreItemFragment on SecondaryStoreItem {
                 itemId
            }
            """;

    private final static String ADDITIONAL_QUERY_NAME = "marketableItems";

    private final static String ADDITIONAL_QUERY_TEMPLATE = """
            marketableItems(
                                limit: $ownedItemsLimit
                                offset: $defaultVarKey
                                sortBy: $ownedItemsSortBy
                                withMarketData: false
                            ) {
                                nodes {
                                    ...MarketableItemFragment
                                    __typename
                                }
                                totalCount
                                __typename
                            }
            """;

    private final GraphQlVariablesService graphQlVariablesService;

    public BuiltGraphQlDocument buildPersonalQueryUserStatsDocument(Integer expectedOwnedItemsQueries) {
        StringBuilder document = new StringBuilder();
        Map<String, Object> resultingVariables = new LinkedHashMap<>();
        Map<String, String> aliasesToFields = new LinkedHashMap<>();

        Map<String, Object> variables = graphQlVariablesService.getDefaultFetchUserStatsVariables();

        if (expectedOwnedItemsQueries <= 0) {
            throw new IllegalArgumentException("Expected owned items queries should be greater than 0");
        }

        StringBuilder variablesSection = new StringBuilder();
        StringBuilder queriesSection = new StringBuilder();

        for (int i = 0; i < expectedOwnedItemsQueries; i++) {
            String varKey = "ownedItemsOffset" + i;
            Integer varValue = i * GraphQlVariablesService.MAX_LIMIT;

            resultingVariables.put(varKey, varValue);

            variablesSection.append(", ");
            variablesSection.append(createOwnedItemsOffsetVariableSection(varKey));

            String alias = ADDITIONAL_QUERY_NAME + i;

            aliasesToFields.put(alias, ADDITIONAL_QUERY_NAME);

            queriesSection.append(createAliasedOwnedItemsQuerySection(alias, varKey));

            aliasesToFields.put(ADDITIONAL_QUERY_NAME + i, ADDITIONAL_QUERY_NAME);
        }

        document.append(QUERY_PART_BEFORE_VARS);

        document.append(variablesSection);

        document.append(QUERY_PART_AFTER_VARS_BEFORE_ADDITIONAL_QUERIES);

        document.append(queriesSection);

        document.append(QUERY_PART_AFTER_ADDITIONAL_QUERIES);

        resultingVariables.putAll(variables);

        return new BuiltGraphQlDocument(
                document.toString(),
                resultingVariables,
                aliasesToFields
        );
    }

    private String createOwnedItemsOffsetVariableSection(String varKey) {
        return "$" + varKey + ": Int";
    }

    private String createAliasedOwnedItemsQuerySection(String alias, String varKey) {
        return alias + ": " + ADDITIONAL_QUERY_TEMPLATE.replace("$defaultVarKey", "$" + varKey);
    }
}
