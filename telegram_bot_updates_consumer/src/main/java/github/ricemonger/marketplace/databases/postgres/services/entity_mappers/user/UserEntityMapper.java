package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShowSettingsProjection;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEntityMapper {

    private final ItemFilterEntityMapper itemFilterEntityMapper;

    public ItemShowSettings mapItemShowSettings(ItemShowSettingsProjection itemShowSettingsProjection, Collection<ItemFilterEntity> itemFilters) {
        return new ItemShowSettings(
                itemShowSettingsProjection.getItemShowMessagesLimit(),
                itemShowSettingsProjection.getItemShowFewInMessageFlag(),
                itemShowSettingsProjection.getItemShowNameFlag(),
                itemShowSettingsProjection.getItemShowItemTypeFlag(),
                itemShowSettingsProjection.getItemShowMaxBuyPrice(),
                itemShowSettingsProjection.getItemShowBuyOrdersCountFlag(),
                itemShowSettingsProjection.getItemShowMinSellPriceFlag(),
                itemShowSettingsProjection.getItemsShowSellOrdersCountFlag(),
                itemShowSettingsProjection.getItemShowPictureFlag(),
                itemFilters.stream().map(itemFilterEntityMapper::createDTO).collect(Collectors.toList()));
    }
}
