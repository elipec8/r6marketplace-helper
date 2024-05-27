package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_credits_amount.Meta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PersonalQueryCreditAmountMapper {

    public int mapCreditAmount(Meta meta) {
        return meta.getQuantity();
    }
}
