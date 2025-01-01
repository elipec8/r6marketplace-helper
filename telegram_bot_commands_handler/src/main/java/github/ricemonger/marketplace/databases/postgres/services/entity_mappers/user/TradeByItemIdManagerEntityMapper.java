package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeByItemIdManagerEntityMapper {

    private final UserPostgresRepository userPostgresRepository;

    private final ItemPostgresRepository itemPostgresRepository;

    public TradeByItemIdManagerEntity createEntity(String chatId, TradeByItemIdManager tradeManager) {
        if(!userPostgresRepository.existsByTelegramUserChatId(chatId)) {
            throw new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found");
        }
        UserEntity userEntity = userPostgresRepository.getReferenceByTelegramUserChatId(chatId);

        if (!itemPostgresRepository.existsById(tradeManager.getItemId())) {
            throw new ItemDoesntExistException("Item with id " + tradeManager.getItemId() + " doesn't exist");
        }
        ItemEntity itemEntity = itemPostgresRepository.getReferenceById(tradeManager.getItemId());

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity.setUser(userEntity);
        tradeByItemIdManagerEntity.setItem(itemEntity);
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
