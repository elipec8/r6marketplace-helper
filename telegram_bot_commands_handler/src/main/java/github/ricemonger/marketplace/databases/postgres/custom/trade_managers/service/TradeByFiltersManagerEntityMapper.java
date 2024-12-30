package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.service;


import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.TradeByFiltersManagerEntity;
import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.UserEntity;
import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeByFiltersManagerEntityMapper {

    private final UserPostgresRepository userPostgresRepository;

    public TradeByFiltersManagerEntity createEntity(String chatId, TradeByFiltersManager tradeManager) {
        UserEntity user = userPostgresRepository.findByTelegramUserChatId(chatId);
        return new TradeByFiltersManagerEntity(user,
                tradeManager.getName(),
                tradeManager.getEnabled(),
                tradeManager.getTradeOperationType(),
                user.getItemFilters().stream().filter(itemFilter -> tradeManager.getAppliedFilters().contains(itemFilter.getName())).toList(),
                tradeManager.getMinDifferenceFromMedianPrice(),
                tradeManager.getMinDifferenceFromMedianPricePercent(),
                tradeManager.getPriorityMultiplier());
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
