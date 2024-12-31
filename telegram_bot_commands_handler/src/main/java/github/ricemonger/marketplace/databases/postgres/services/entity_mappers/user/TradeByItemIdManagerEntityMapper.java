package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeByItemIdManagerEntityMapper {

    private final UserPostgresRepository userRepository;

    private final ItemPostgresPostgresRepository itemRepository;

    public TradeByItemIdManagerEntity createEntity(String chatId, TradeByItemIdManager tradeManager) {
        TradeByItemIdManagerEntity tradeByItemIdManagerEntity = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity.setUser(userRepository.findByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist")));
        tradeByItemIdManagerEntity.setItem(itemRepository.findById(tradeManager.getItemId()).orElseThrow(() -> new ItemDoesntExistException("Item with id " + tradeManager.getItemId() + " doesn't exist")));
        tradeByItemIdManagerEntity.setEnabled(tradeManager.getEnabled());
        tradeByItemIdManagerEntity.setTradeOperationType(tradeManager.getTradeOperationType());
        tradeByItemIdManagerEntity.setSellBoundaryPrice(tradeManager.getSellBoundaryPrice());
        tradeByItemIdManagerEntity.setBuyBoundaryPrice(tradeManager.getBuyBoundaryPrice());
        tradeByItemIdManagerEntity.setPriorityMultiplier(tradeManager.getPriorityMultiplier());
        return tradeByItemIdManagerEntity;
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
