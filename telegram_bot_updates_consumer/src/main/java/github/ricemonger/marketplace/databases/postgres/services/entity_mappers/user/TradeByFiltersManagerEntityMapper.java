package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.repositories.CustomUserPostgresRepository;
import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeByFiltersManagerEntityMapper {

    private final CustomUserPostgresRepository customUserPostgresRepository;

    public TradeByFiltersManagerEntity createEntity(String chatId, TradeByFiltersManager tradeManager) {
        if (!customUserPostgresRepository.existsByTelegramUserChatId(chatId)) {
            throw new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found");
        }
        UserEntity userEntity = customUserPostgresRepository.getReferenceByTelegramUserChatId(chatId);

        TradeByFiltersManagerEntity entity = new TradeByFiltersManagerEntity();
        entity.setUser(userEntity);
        entity.setName(tradeManager.getName());
        entity.setEnabled(tradeManager.getEnabled());
        entity.setTradeOperationType(tradeManager.getTradeOperationType());
        entity.setAppliedFilters(userEntity.getItemFilters().stream().filter(itemFilter -> tradeManager.getAppliedFilters().contains(itemFilter.getName())).toList());
        entity.setMinDifferenceFromMedianPrice(tradeManager.getMinDifferenceFromMedianPrice());
        entity.setMinDifferenceFromMedianPricePercent(tradeManager.getMinDifferenceFromMedianPricePercent());
        entity.setPriorityMultiplier(tradeManager.getPriorityMultiplier());
        return entity;
    }

    public TradeByFiltersManager createDTO(TradeByFiltersManagerEntity entity) {
        return new TradeByFiltersManager(entity.getName(),
                entity.getEnabled(),
                entity.getTradeOperationType(),
                entity.getAppliedFilters().stream().map(ItemFilterEntity::getName).toList(),
                entity.getMinDifferenceFromMedianPrice(),
                entity.getMinDifferenceFromMedianPricePercent(),
                entity.getPriorityMultiplier());
    }
}
