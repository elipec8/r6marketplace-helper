package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.TradeLimitations;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.tradeLimitations.sell.ResaleLocks;
import github.ricemonger.utils.dtos.LockedItem;
import github.ricemonger.utils.dtos.UserTransactionsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class PersonalQueryLockedItemsMapper {

    private final SimpleDateFormat sdf;

    public PersonalQueryLockedItemsMapper(UbiServiceConfiguration ubiServiceConfiguration) {
        this.sdf = new SimpleDateFormat(ubiServiceConfiguration.getDateFormat());
    }

    public Collection<LockedItem> mapLockedItems(TradeLimitations tradeLimitations) {
        if (tradeLimitations.getSell() != null) {
            return tradeLimitations.getSell().getResaleLocks().stream().map(this::mapLockedItem).toList();
        } else {
            log.error("Sell trade limitations is null, no resale locks to map.");
            return List.of();
        }
    }

    public LockedItem mapLockedItem(ResaleLocks resaleLocks) {
        LockedItem.LockedItemBuilder builder = LockedItem.builder()
                .itemId(resaleLocks.getItemId());
        try {
            builder.expiresAt(sdf.parse(resaleLocks.getExpiresAt()));

        } catch (ParseException e) {
            builder.expiresAt(new Date(0));
            log.error("Error parsing date: {}", e.getMessage());
        }

        return builder.build();
    }

    public UserTransactionsInfo mapUserTransactionsInfo(TradeLimitations tradeLimitations) {
        UserTransactionsInfo.UserTransactionsInfoBuilder builder = UserTransactionsInfo.builder();
        if (tradeLimitations.getBuy() != null) {
            builder.buyResolvedTransactionCount(tradeLimitations.getBuy().getResolvedTransactionCount())
                    .buyActiveTransactionCount(tradeLimitations.getBuy().getActiveTransactionCount());
        } else {
            builder.buyResolvedTransactionCount(0)
                    .buyActiveTransactionCount(0);
            log.error("Buy trade limitations is null, no user transactions info to map.");
        }
        if (tradeLimitations.getSell() != null) {
            builder.sellResolvedTransactionCount(tradeLimitations.getSell().getResolvedTransactionCount())
                    .sellActiveTransactionCount(tradeLimitations.getSell().getActiveTransactionCount());
        } else {
            builder.sellResolvedTransactionCount(0)
                    .sellActiveTransactionCount(0);
            log.error("Sell trade limitations is null, no user transactions info to map.");
        }
        return builder.build();
    }
}
