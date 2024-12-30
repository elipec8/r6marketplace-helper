package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.custom.tg_user_input_group_and_state.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.TelegramUser;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInputStateAndGroup;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramUserEntityMapper {

    private final ItemFilterEntityMapper itemFilterEntityMapper;

    private final UserPostgresRepository userRepository;

    public github.ricemonger.marketplace.databases.postgres.custom.settings.entities.TelegramUserEntity createNewEntityForNewUser(String chatId) {
        UserEntity user = userRepository.save(new UserEntity());
        github.ricemonger.marketplace.databases.postgres.custom.settings.entities.TelegramUserEntity entity = new github.ricemonger.marketplace.databases.postgres.custom.settings.entities.TelegramUserEntity();
        entity.setChatId(chatId);
        entity.setUser(user);
        return entity;
    }

    public TelegramUser createTelegramUser(github.ricemonger.marketplace.databases.postgres.custom.settings.entities.TelegramUserEntity entity) {
        String chatId = entity.getChatId();
        InputState inputState = entity.getInputState();
        InputGroup inputGroup = entity.getInputGroup();
        Boolean publicNotificationsFlag = entity.getUser().getPublicNotificationsEnabledFlag();
        Integer itemShowMessagesLimit = entity.getItemShowMessagesLimit();
        Boolean itemShowFewInMessageFlag = entity.getItemShowFewInMessageFlag();
        Boolean itemShowNameFlag = entity.getUser().getItemShowNameFlag();
        Boolean itemShowItemTypeFlag = entity.getUser().getItemShowItemTypeFlag();
        Boolean itemShowMaxBuyPrice = entity.getUser().getItemShowMaxBuyPrice();
        Boolean itemShowBuyOrdersCountFlag = entity.getUser().getItemShowBuyOrdersCountFlag();
        Boolean itemShowMinSellPriceFlag = entity.getUser().getItemShowMinSellPriceFlag();
        Boolean itemsShowSellOrdersCountFlag = entity.getUser().getItemsShowSellOrdersCountFlag();
        Boolean itemShowPictureFlag = entity.getUser().getItemShowPictureFlag();
        List<ItemFilter> itemShowAppliedFilters = new ArrayList<>();
        if (entity.getUser().getItemShowAppliedFilters() != null) {
            itemShowAppliedFilters = (entity.getUser().getItemShowAppliedFilters().stream().map(itemFilterEntityMapper::createDTO).toList());
        }

        Boolean newManagersAreActiveFlag = entity.getUser().getNewManagersAreActiveFlag();
        Boolean managingEnabledFlag = entity.getUser().getManagingEnabledFlag();
        return new TelegramUser(chatId,
                inputState,
                inputGroup,
                publicNotificationsFlag,
                itemShowMessagesLimit,
                itemShowFewInMessageFlag,
                itemShowNameFlag,
                itemShowItemTypeFlag,
                itemShowMaxBuyPrice,
                itemShowBuyOrdersCountFlag,
                itemShowMinSellPriceFlag,
                itemsShowSellOrdersCountFlag,
                itemShowPictureFlag,
                itemShowAppliedFilters,
                newManagersAreActiveFlag,
                managingEnabledFlag);
    }

    public ItemShowSettings createItemShowSettings(github.ricemonger.marketplace.databases.postgres.custom.settings.entities.TelegramUserEntity entity) {
        Integer messageLimit = entity.getItemShowMessagesLimit();
        Boolean itemShowFewInMessageFlag = entity.getItemShowFewInMessageFlag();
        Boolean itemShowNameFlag = entity.getUser().getItemShowNameFlag();
        Boolean itemShowItemTypeFlag = entity.getUser().getItemShowItemTypeFlag();
        Boolean itemShowMaxBuyPrice = entity.getUser().getItemShowMaxBuyPrice();
        Boolean itemShowBuyOrdersCountFlag = entity.getUser().getItemShowBuyOrdersCountFlag();
        Boolean itemShowMinSellPriceFlag = entity.getUser().getItemShowMinSellPriceFlag();
        Boolean itemsShowSellOrdersCountFlag = entity.getUser().getItemsShowSellOrdersCountFlag();
        Boolean itemShowPictureFlag = entity.getUser().getItemShowPictureFlag();

        List<ItemFilter> itemShowAppliedFilters = new ArrayList<>();

        if (entity.getItemShowAppliedFilters() != null) {
            itemShowAppliedFilters.addAll(entity.getItemShowAppliedFilters().stream().map(itemFilterEntityMapper::createDTO).toList());
        }

        return new ItemShowSettings(messageLimit,
                itemShowFewInMessageFlag,
                itemShowNameFlag,
                itemShowItemTypeFlag,
                itemShowMaxBuyPrice,
                itemShowBuyOrdersCountFlag,
                itemShowMinSellPriceFlag,
                itemsShowSellOrdersCountFlag,
                itemShowPictureFlag,
                itemShowAppliedFilters);
    }

    public TradeManagersSettings createTradeManagersSettings(github.ricemonger.marketplace.databases.postgres.custom.settings.entities.TelegramUserEntity entity) {
        return new TradeManagersSettings(entity.getUser().getNewManagersAreActiveFlag(), entity.getUser().getManagingEnabledFlag());
    }

    public TelegramUserInputStateAndGroup createInputStateAndGroupDTO(TelegramUserEntity entity) {
        return new TelegramUserInputStateAndGroup(entity.getChatId(), entity.getInputState(), entity.getInputGroup());
    }

    public TelegramUserEntity createInputStateAndGroupEntity(String chatId, InputState inputState, InputGroup inputGroup) {
        return new TelegramUserEntity(chatId, inputState, inputGroup);
    }
}
