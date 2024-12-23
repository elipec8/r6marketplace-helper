package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.personal.TradeByFiltersManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeByFiltersManagerEntityMapper {

    private final UserPostgresRepository userPostgresRepository;

    private final ItemFilterEntityMapper itemFilterEntityMapper;

    public TradeByFiltersManagerEntity createEntityForTelegramUser(String chatId, TradeByFiltersManager dto) {
        UserEntity user = userPostgresRepository.findByTelegramUserChatId(chatId);
        return new TradeByFiltersManagerEntity(user,
                dto.getName(),
                dto.getEnabled(),
                dto.getTradeOperationType(),
                dto.getAppliedFilters().stream().map(filter -> itemFilterEntityMapper.createEntityForUser(user, filter)).toList(),
                dto.getMinDifferenceFromMedianPrice(),
                dto.getMinDifferenceFromMedianPricePercent(),
                dto.getPriorityMultiplier());
    }

    public TradeByFiltersManager createDTO(TradeByFiltersManagerEntity entity) {
        return new TradeByFiltersManager(entity.getName(),
                entity.getEnabled(),
                entity.getTradeOperationType(),
                entity.getAppliedFilters().stream().map(itemFilterEntityMapper::createDTO).toList(),
                entity.getMinDifferenceFromMedianPrice(),
                entity.getMinDifferenceFromMedianPricePercent(),
                entity.getPriorityMultiplier());
    }
}
