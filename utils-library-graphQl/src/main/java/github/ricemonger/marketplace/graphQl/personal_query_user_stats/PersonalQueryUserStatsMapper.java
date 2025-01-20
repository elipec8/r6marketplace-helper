package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.Meta;
import github.ricemonger.utils.DTOs.personal.UserTradesLimitations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PersonalQueryUserStatsMapper {

    private final GraphQlCommonValuesService graphQlCommonValuesService;

    public UserTradesLimitations mapUserStats(Meta meta, Map<String, String> aliasesToFields, String profileId) {
        return null;
    }
}
