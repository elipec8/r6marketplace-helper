package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.TelegramUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramUserPostgresMapper {

    private final ItemFilterPostgresMapper itemFilterPostgresMapper;

    public Collection<TelegramUserEntity> mapTelegramUserEntities(Collection<TelegramUser> telegramUsers) {
        if (telegramUsers == null || telegramUsers.isEmpty()) {
            return List.of();
        } else {
            return telegramUsers.stream().map(this::mapTelegramUserEntity).toList();
        }
    }

    public TelegramUserEntity mapTelegramUserEntity(TelegramUser telegramUser) {
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();

        telegramUserEntity.setChatId(telegramUser.getChatId());
        telegramUserEntity.setInputState(telegramUser.getInputState());
        telegramUserEntity.setInputGroup(telegramUser.getInputGroup());
        telegramUserEntity.setPublicNotificationsEnabledFlag(telegramUser.isPublicNotificationsEnabledFlag());

        telegramUserEntity.setItemShowMessagesLimit(telegramUser.getItemShowMessagesLimit());
        telegramUserEntity.setItemShowFewInMessageFlag(telegramUser.isItemShowFewInMessageFlag());

        telegramUserEntity.setItemShowNameFlag(telegramUser.isItemShowNameFlag());
        telegramUserEntity.setItemShowItemTypeFlag(telegramUser.isItemShowItemTypeFlag());
        telegramUserEntity.setItemShowMaxBuyPrice(telegramUser.isItemShowMaxBuyPrice());
        telegramUserEntity.setItemShowBuyOrdersCountFlag(telegramUser.isItemShowBuyOrdersCountFlag());
        telegramUserEntity.setItemShowMinSellPriceFlag(telegramUser.isItemShowMinSellPriceFlag());
        telegramUserEntity.setItemsShowSellOrdersCountFlag(telegramUser.isItemsShowSellOrdersCountFlag());
        telegramUserEntity.setItemShowPictureFlag(telegramUser.isItemShowPictureFlag());

        telegramUserEntity.setItemShowAppliedFilters(new ArrayList<>(itemFilterPostgresMapper.mapItemFilterEntities(telegramUser.getItemShowAppliedFilters())));

        return telegramUserEntity;
    }

    public Collection<TelegramUser> mapTelegramUsers(Collection<TelegramUserEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        } else {
            return entities.stream().map(this::mapTelegramUser).toList();
        }
    }

    public TelegramUser mapTelegramUser(TelegramUserEntity entity) {
        TelegramUser telegramUser = new TelegramUser();

        telegramUser.setChatId(entity.getChatId());

        telegramUser.setInputState(entity.getInputState());
        telegramUser.setInputGroup(entity.getInputGroup());

        telegramUser.setPublicNotificationsEnabledFlag(entity.isPublicNotificationsEnabledFlag());

        telegramUser.setItemShowFewInMessageFlag(entity.isItemShowFewInMessageFlag());

        telegramUser.setItemShowMessagesLimit(entity.getItemShowMessagesLimit());

        telegramUser.setItemShowAppliedFilters(new ArrayList<>(itemFilterPostgresMapper.mapItemFilters(entity.getItemShowAppliedFilters())));

        telegramUser.setItemShowNameFlag(entity.isItemShowNameFlag());
        telegramUser.setItemShowItemTypeFlag(entity.isItemShowItemTypeFlag());
        telegramUser.setItemShowMaxBuyPrice(entity.isItemShowMaxBuyPrice());
        telegramUser.setItemShowBuyOrdersCountFlag(entity.isItemShowBuyOrdersCountFlag());
        telegramUser.setItemShowMinSellPriceFlag(entity.isItemShowMinSellPriceFlag());
        telegramUser.setItemsShowSellOrdersCountFlag(entity.isItemsShowSellOrdersCountFlag());
        telegramUser.setItemShowPictureFlag(entity.isItemShowPictureFlag());

        return telegramUser;
    }
}
