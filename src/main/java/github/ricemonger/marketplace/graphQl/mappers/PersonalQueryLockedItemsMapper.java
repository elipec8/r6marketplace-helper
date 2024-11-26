package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.DTOs.personal_query_locked_items.TradeLimitations;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_locked_items.tradeLimitations.sell.ResaleLocks;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.UserTransactionsCount;
import github.ricemonger.utils.DTOs.items.ItemResaleLock;
import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalLockedItemsMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonalQueryLockedItemsMapper {

    private final CommonValuesService commonValuesService;

    public List<ItemResaleLock> mapLockedItems(TradeLimitations tradeLimitations) throws GraphQlPersonalLockedItemsMappingException {
        if (tradeLimitations == null) {
            throw new GraphQlPersonalLockedItemsMappingException("Trade limitations is null");
        }

        if (tradeLimitations.getSell() == null || tradeLimitations.getSell().getResaleLocks() == null) {
            throw new GraphQlPersonalLockedItemsMappingException("Sell or resale locks is null in trade limitations-" + tradeLimitations);
        } else {
            return tradeLimitations.getSell().getResaleLocks().stream().map(this::mapLockedItem).toList();
        }
    }

    public ItemResaleLock mapLockedItem(ResaleLocks resaleLocks) throws GraphQlPersonalLockedItemsMappingException {
        if (resaleLocks == null || resaleLocks.getItemId() == null || resaleLocks.getExpiresAt() == null) {
            throw new GraphQlPersonalLockedItemsMappingException("Resale locks or one of it's fields is null:" + resaleLocks);
        }
        ItemResaleLock result = new ItemResaleLock();
        result.setItemId(resaleLocks.getItemId());
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
            result.setExpiresAt(LocalDateTime.parse(resaleLocks.getExpiresAt(), dtf));

        } catch (DateTimeParseException e) {
            result.setExpiresAt(LocalDateTime.MIN);
            log.error("Error parsing date: {}", e.getMessage());
        }

        return result;
    }

    public UserTransactionsCount mapUserTransactionsCount(TradeLimitations tradeLimitations) throws GraphQlPersonalLockedItemsMappingException {
        if (tradeLimitations == null
            || tradeLimitations.getBuy() == null
            || tradeLimitations.getSell() == null
            || tradeLimitations.getBuy().getActiveTransactionCount() == null || tradeLimitations.getBuy().getResolvedTransactionCount() == null
            || tradeLimitations.getSell().getActiveTransactionCount() == null || tradeLimitations.getSell().getResolvedTransactionCount() == null) {
            throw new GraphQlPersonalLockedItemsMappingException("Trade limitation or one of its fields is null:" + tradeLimitations);
        }

        UserTransactionsCount result = new UserTransactionsCount();
        result.setBuyResolvedTransactionCount(tradeLimitations.getBuy().getResolvedTransactionCount());
        result.setBuyActiveTransactionCount(tradeLimitations.getBuy().getActiveTransactionCount());
        result.setSellResolvedTransactionCount(tradeLimitations.getSell().getResolvedTransactionCount());
        result.setSellActiveTransactionCount(tradeLimitations.getSell().getActiveTransactionCount());

        return result;
    }
}
