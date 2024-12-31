package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShowSettingsProjection;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityMapper {

    private final ItemFilterEntityMapper itemFilterEntityMapper;

    public ItemShowSettings mapItemShowSettings(ItemShowSettingsProjection itemShowSettingsProjection) {
        return new ItemShowSettings(
                itemShowSettingsProjection.getItemShowMessagesLimit(),
                itemShowSettingsProjection.getItemShowFewInMessageFlag(),
                itemShowSettingsProjection.getShownFieldsSettings().getItemShowNameFlag(),
                itemShowSettingsProjection.getShownFieldsSettings().getItemShowItemTypeFlag(),
                itemShowSettingsProjection.getShownFieldsSettings().getItemShowMaxBuyPrice(),
                itemShowSettingsProjection.getShownFieldsSettings().getItemShowBuyOrdersCountFlag(),
                itemShowSettingsProjection.getShownFieldsSettings().getItemShowMinSellPriceFlag(),
                itemShowSettingsProjection.getShownFieldsSettings().getItemsShowSellOrdersCountFlag(),
                itemShowSettingsProjection.getShownFieldsSettings().getItemShowPictureFlag(),
                itemShowSettingsProjection.getItemShowAppliedFilters().stream()
                        .map(itemFilterEntityMapper::createDTO)
                        .toList());
    }
}
