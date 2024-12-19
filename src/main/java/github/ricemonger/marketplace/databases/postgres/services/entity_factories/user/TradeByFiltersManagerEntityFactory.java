package github.ricemonger.marketplace.databases.postgres.services.entity_factories.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeByFiltersManagerEntityFactory {

    private final UserPostgresRepository userPostgresRepository;

    private final ItemFilterEntityFactory itemFilterEntityFactory;

    public TradeByFiltersManagerEntity createEntityForTelegramUser(String chatId, TradeByFiltersManager dto) {
        UserEntity user = userPostgresRepository.findByTelegramUserChatId(chatId);
        return new TradeByFiltersManagerEntity(user,
                dto.getName(),
                dto.isEnabled(),
                dto.getTradeOperationType(),
                dto.getAppliedFilters().stream().map(filter -> itemFilterEntityFactory.createEntityForUser(user, filter)).toList(),
                dto.getMinDifferenceFromMedianPrice(),
                dto.getMinDifferenceFromMedianPricePercent(),
                dto.getPriorityMultiplier());
    }

    public TradeByFiltersManager createDTO(TradeByFiltersManagerEntity entity) {
        return new TradeByFiltersManager(entity.getName(),
                entity.isEnabled(),
                entity.getTradeOperationType(),
                entity.getAppliedFilters().stream().map(itemFilterEntityFactory::createDTO).toList(),
                entity.getMinDifferenceFromMedianPrice(),
                entity.getMinDifferenceFromMedianPricePercent(),
                entity.getPriorityMultiplier());
    }
}
