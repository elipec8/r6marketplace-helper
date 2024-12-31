package github.ricemonger.marketplace.databases.postgres.dto_projections;

import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemShowSettingsProjection {
    private Integer itemShowMessagesLimit;
    private Boolean itemShowFewInMessageFlag;

    private ItemShownFieldsSettings shownFieldsSettings = new ItemShownFieldsSettings();

    private Collection<ItemFilterEntity> itemShowAppliedFilters = new ArrayList<>();

    public ItemShowSettingsProjection(Integer itemShowMessagesLimit,
                            Boolean itemShowFewInMessageFlag,
                            Boolean itemShowNameFlag,
                            Boolean itemShowItemTypeFlag,
                            Boolean itemShowMaxBuyPrice,
                            Boolean itemShowBuyOrdersCountFlag,
                            Boolean itemShowMinSellPriceFlag,
                            Boolean itemsShowSellOrdersCountFlag,
                            Boolean itemShowPictureFlag,
                            Collection<ItemFilterEntity> itemShowAppliedFilters) {
        this.itemShowMessagesLimit = itemShowMessagesLimit;
        this.itemShowFewInMessageFlag = itemShowFewInMessageFlag;
        this.shownFieldsSettings = new ItemShownFieldsSettings(
                itemShowNameFlag,
                itemShowItemTypeFlag,
                itemShowMaxBuyPrice,
                itemShowBuyOrdersCountFlag,
                itemShowMinSellPriceFlag,
                itemsShowSellOrdersCountFlag,
                itemShowPictureFlag
        );
        this.itemShowAppliedFilters = itemShowAppliedFilters;
    }
}
