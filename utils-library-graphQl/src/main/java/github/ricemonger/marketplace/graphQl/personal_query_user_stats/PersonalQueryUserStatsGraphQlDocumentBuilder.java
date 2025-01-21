package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PersonalQueryUserStatsGraphQlDocumentBuilder {

    private final static String personalQueryFetchUserStatsPartBeforeOwnedItemsOffsetVariables = "query GetUserStats(";

    private final static String personalQueryFetchUserStatsPartAfterOwnedItemsOffsetVariablesBeforeQueries = """ 
            ) {
            game(spaceId: $spaceId) {
                viewer {
                    meta { 
            """;

    private final static String personalQueryFetchUserStatsPartAfterQueries = """ 
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
            
                            trades(
                                limit: $tradesLimit
                                offset: $tradesOffset
                                filterBy: {states: [Created, Succeeded, Failed]}
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
            
            fragment SecondaryStoreItemFragment on SecondaryStoreItem {
                id
            }
            
            fragment TradeFragment on Trade {
                tradeId
                state
                category
                expiresAt
                lastModifiedAt
                tradeItems {
                    item {
                        ...SecondaryStoreItemFragment
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
            """;

    private final static String ownedItemsQueryName = "marketableItems";

    private final static String ownedItemsQuery = """
            marketableItems(
                                limit: $ownedItemsLimit
                                offset: $ownedItemsOffset
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

    public BuiltGraphQlDocuments buildPersonalQueryUserStatsDocument(Integer ownedItemsExpectedAmount) {
        StringBuilder document = new StringBuilder();
        Map<String, Object> additionalVariables = new LinkedHashMap<>();
        Map<String, String> ownedItemsQueryAliasesToFields = new LinkedHashMap<>();

        Map<String, Object> variables = graphQlVariablesService.getDefaultFetchUserStatsVariables();

        int expectedOwnedItemsQueries = ownedItemsExpectedAmount / GraphQlVariablesService.MAX_LIMIT;

        StringBuilder variablesSection = new StringBuilder();
        StringBuilder queriesSection = new StringBuilder();

        for (int i = 0; i < expectedOwnedItemsQueries; i++) {
            additionalVariables.put("ownedItemsOffset" + i, i * GraphQlVariablesService.MAX_LIMIT);
            ownedItemsQueryAliasesToFields.put(ownedItemsQueryName + i, ownedItemsQueryName);
        }
        variables.putAll(additionalVariables);

        document.append(personalQueryFetchUserStatsPartBeforeOwnedItemsOffsetVariables);

        document.append(createOwnedItemsOffsetsVariablesSection(variables));

        document.append(personalQueryFetchUserStatsPartAfterOwnedItemsOffsetVariablesBeforeQueries);

        document.append(createAliasedOwnedItemsQueriesSection(ownedItemsQueryAliasesToFields));

        document.append(personalQueryFetchUserStatsPartAfterQueries);

        return new BuiltGraphQlDocuments(
                document.toString(),
                variables,
                ownedItemsQueryAliasesToFields
        );
    }

    private String createOwnedItemsOffsetsVariablesSection(Map<String, Object> variables) {
       StringBuilder section = new StringBuilder();
        for (Map.Entry<String, Object> variable : variables.entrySet()) {
            section.append("$").append(variable.getKey()).append(": ");
            if(variable.getValue() instanceof Integer) {
                section.append("Int");
            }  else {
                throw new IllegalArgumentException("Unsupported variable type as owned items offset variable: " + variable.getValue().getClass());
            }

        }
        return section.toString();
    }

    private String createAliasedOwnedItemsQueriesSection(Map<String, String> aliasesToFields) {
        StringBuilder section = new StringBuilder();
        for (Map.Entry<String, String> aliasToField : aliasesToFields.entrySet()) {
            section.append(aliasToField.getKey()).append(": ").append(ownedItemsQuery.replace("$ownedItemsOffset", aliasToField.getValue())).append("\n");
        }
        return section.toString();
    }
}
