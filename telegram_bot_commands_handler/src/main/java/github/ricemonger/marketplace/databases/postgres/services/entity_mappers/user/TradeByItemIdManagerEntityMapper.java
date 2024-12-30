package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeByItemIdManagerEntityMapper {

    private final UserPostgresRepository userRepository;

    private final ItemPostgresRepository itemRepository;

    public TradeByItemIdManagerEntity createEntityForTelegramUser(String chatId, TradeByItemIdManager tradeManager) {
        return new TradeByItemIdManagerEntity(
                userRepository.findByTelegramUserChatId(chatId),
                itemRepository.findById(tradeManager.getItemId()).orElseThrow(() -> new ItemDoesntExistException("Item with id " + tradeManager.getItemId() + " doesn't exist")),
                tradeManager.getEnabled(),
                tradeManager.getTradeOperationType(),
                tradeManager.getSellBoundaryPrice(),
                tradeManager.getBuyBoundaryPrice(),
                tradeManager.getPriorityMultiplier()
        );
    }

    public TradeByItemIdManager createDTO(TradeByItemIdManagerEntity entity) {
        return new TradeByItemIdManager(
                entity.getItemId_(),
                entity.getEnabled(),
                entity.getTradeOperationType(),
                entity.getSellBoundaryPrice(),
                entity.getBuyBoundaryPrice(),
                entity.getPriorityMultiplier()
        );
    }
}
