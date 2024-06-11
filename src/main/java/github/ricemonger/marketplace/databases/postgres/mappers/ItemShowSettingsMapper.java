package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemShowSettingsMapper {

    private final ItemFilterPostgresMapper itemFilterPostgresMapper;

    public ItemShowSettings mapItemShowSettings(TelegramUserEntity telegramUserEntity) {
        ItemShowSettings itemShowSettings = new ItemShowSettings();

        itemShowSettings.setItemShowMessagesLimit(telegramUserEntity.getItemShowMessagesLimit());
        itemShowSettings.setItemShowFewInMessageFlag(telegramUserEntity.isItemShowFewInMessageFlag());

        itemShowSettings.setItemShowNameFlag(telegramUserEntity.isItemShowNameFlag());
        itemShowSettings.setItemShowItemTypeFlag(telegramUserEntity.isItemShowItemTypeFlag());
        itemShowSettings.setItemShowMaxBuyPrice(telegramUserEntity.isItemShowMaxBuyPrice());
        itemShowSettings.setItemShowBuyOrdersCountFlag(telegramUserEntity.isItemShowBuyOrdersCountFlag());
        itemShowSettings.setItemShowMinSellPriceFlag(telegramUserEntity.isItemShowMinSellPriceFlag());
        itemShowSettings.setItemsShowSellOrdersCountFlag(telegramUserEntity.isItemsShowSellOrdersCountFlag());
        itemShowSettings.setItemShowPictureFlag(telegramUserEntity.isItemShowPictureFlag());

        itemShowSettings.setItemShowAppliedFilters(itemFilterPostgresMapper.mapItemFilters(telegramUserEntity.getItemShowAppliedFilters()));

        return itemShowSettings;
    }

    public TelegramUserEntity mapTelegramUserEntity(String chatId, ItemShownFieldsSettings settings){
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId(chatId);
        telegramUserEntity.setItemShowNameFlag(settings.isItemShowNameFlag());
        telegramUserEntity.setItemShowItemTypeFlag(settings.isItemShowItemTypeFlag());
        telegramUserEntity.setItemShowMaxBuyPrice(settings.isItemShowMaxBuyPrice());
        telegramUserEntity.setItemShowBuyOrdersCountFlag(settings.isItemShowBuyOrdersCountFlag());
        telegramUserEntity.setItemShowMinSellPriceFlag(settings.isItemShowMinSellPriceFlag());
        telegramUserEntity.setItemsShowSellOrdersCountFlag(settings.isItemsShowSellOrdersCountFlag());
        telegramUserEntity.setItemShowPictureFlag(settings.isItemShowPictureFlag());
        return telegramUserEntity;
    }
}
