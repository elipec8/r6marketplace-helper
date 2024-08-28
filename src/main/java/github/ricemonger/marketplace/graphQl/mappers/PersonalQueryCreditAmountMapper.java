package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_credits_amount.Meta;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalCreditAmountMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PersonalQueryCreditAmountMapper {

    public int mapCreditAmount(Meta meta) throws GraphQlPersonalCreditAmountMappingException {
        if (meta == null || meta.getQuantity() == null) {
            throw new GraphQlPersonalCreditAmountMappingException("Meta is null");
        }
        return meta.getQuantity();
    }
}
