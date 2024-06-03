package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.TradeLimitations;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.tradeLimitations.sell.ResaleLocks;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.LockedItem;
import github.ricemonger.utils.dtos.UserTransactionsInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonalQueryLockedItemsMapper {

    private final CommonValuesService commonValuesService;

    public Collection<LockedItem> mapLockedItems(TradeLimitations tradeLimitations) {
        if (tradeLimitations.getSell() != null) {
            return tradeLimitations.getSell().getResaleLocks().stream().map(this::mapLockedItem).toList();
        } else {
            log.error("Sell trade limitations is null, no resale locks to map.");
            return List.of();
        }
    }

    public LockedItem mapLockedItem(ResaleLocks resaleLocks) {
        LockedItem result = new LockedItem();
        result.setItemId(resaleLocks.getItemId());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
            result.setExpiresAt(sdf.parse(resaleLocks.getExpiresAt()));

        } catch (ParseException e) {
            result.setExpiresAt(new Date(0));
            log.error("Error parsing date: {}", e.getMessage());
        }

        return result;
    }

    public UserTransactionsInfo mapUserTransactionsInfo(TradeLimitations tradeLimitations) {
        UserTransactionsInfo result = new UserTransactionsInfo();
        if (tradeLimitations.getBuy() != null) {
            result.setBuyResolvedTransactionCount(tradeLimitations.getBuy().getResolvedTransactionCount());
            result.setBuyActiveTransactionCount(tradeLimitations.getBuy().getActiveTransactionCount());
        } else {
            result.setBuyResolvedTransactionCount(0);
            result.setBuyActiveTransactionCount(0);
            log.error("Buy trade limitations is null, no user transactions info to map.");
        }
        if (tradeLimitations.getSell() != null) {
            result.setSellResolvedTransactionCount(tradeLimitations.getSell().getResolvedTransactionCount());
            result.setSellActiveTransactionCount(tradeLimitations.getSell().getActiveTransactionCount());
        } else {
            result.setSellResolvedTransactionCount(0);
            result.setSellActiveTransactionCount(0);
            log.error("Sell trade limitations is null, no user transactions info to map.");
        }
        return result;
    }
}
