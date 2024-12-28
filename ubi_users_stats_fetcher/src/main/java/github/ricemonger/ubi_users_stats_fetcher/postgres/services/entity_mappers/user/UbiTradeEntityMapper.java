package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.ItemIdEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.UbiTradeEntity;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UbiTradeEntityMapper {

    public UbiTradeEntity createEntity(UbiTrade ubiTrade, List<ItemIdEntity> existingItems) {
        return new UbiTradeEntity(
                ubiTrade.getTradeId(),
                ubiTrade.getState(),
                ubiTrade.getCategory(),
                ubiTrade.getExpiresAt(),
                ubiTrade.getLastModifiedAt(),
                existingItems.stream().filter(item -> item.getItemId().equals(ubiTrade.getItemId())).findFirst().orElseThrow(() -> new ItemDoesntExistException("Item with id " + ubiTrade.getItemId() + " not found")),
                ubiTrade.getSuccessPaymentPrice(),
                ubiTrade.getSuccessPaymentFee(),
                ubiTrade.getProposedPaymentPrice(),
                ubiTrade.getProposedPaymentFee()
        );
    }
}
