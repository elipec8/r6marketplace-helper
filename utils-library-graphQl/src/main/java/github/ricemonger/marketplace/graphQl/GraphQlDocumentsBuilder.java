package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.graphQl.personal_query_user_stats.BuiltGraphQlDocuments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GraphQlDocumentsBuilder {

    private final static String personalQueryFetchUserStatsPart1 = """  
            """;

    private final static String personalQueryFetchUserStatsPart2 = """  
            """;

    private final static String personalQueryFetchUserStatsPart3 = """  
            """;

    private final GraphQlVariablesService graphQlVariablesService;

    public BuiltGraphQlDocuments buildPersonalQueryUserStatsDocument(Integer ownedItemsExpectedAmount) {
        StringBuilder document = new StringBuilder();
        Map<String, Object> variables = new LinkedHashMap<>();
        Map<String, String> aliasesToFields = new LinkedHashMap<>();

        int expectedOwnedItemsQueries = ownedItemsExpectedAmount / GraphQlVariablesService.MAX_LIMIT;

        document.append(personalQueryFetchUserStatsPart1);


        return null;
    }
}
