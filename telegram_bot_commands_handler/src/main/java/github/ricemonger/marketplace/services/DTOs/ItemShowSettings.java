package github.ricemonger.marketplace.services.DTOs;

import github.ricemonger.utils.DTOs.personal.ItemFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemShowSettings {
    private Integer itemShowMessagesLimit;
    private Boolean itemShowFewInMessageFlag;

    private ItemShownFieldsSettings shownFieldsSettings = new ItemShownFieldsSettings();

    private Collection<ItemFilter> itemShowAppliedFilters = new ArrayList<>();

    public ItemShowSettings(Integer itemShowMessagesLimit,
                            Boolean itemShowFewInMessageFlag,
                            Boolean itemShowNameFlag,
                            Boolean itemShowItemTypeFlag,
                            Boolean itemShowMaxBuyPrice,
                            Boolean itemShowBuyOrdersCountFlag,
                            Boolean itemShowMinSellPriceFlag,
                            Boolean itemsShowSellOrdersCountFlag,
                            Boolean itemShowPictureFlag,
                            Collection<ItemFilter> itemShowAppliedFilters) {
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

    public void setItemShowNameFlag(Boolean itemShowNameFlag) {
        shownFieldsSettings.setItemShowNameFlag(itemShowNameFlag);
    }

    public Integer getActiveFieldsCount() {
        return shownFieldsSettings == null ? null : shownFieldsSettings.getActiveFieldsCount();
    }

    public String toHandsomeString() {
        String shownFields;
        if (shownFieldsSettings == null) {
            shownFields = "null";
        } else {
            shownFields = shownFieldsSettings.toString();
        }

        String appliedFilters;
        if (itemShowAppliedFilters == null) {
            appliedFilters = "null";
        } else {
            appliedFilters = itemShowAppliedFilters.stream().map(ItemFilter::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        }

        return "Messages limit: " + itemShowMessagesLimit + "\n" +
               "Few items in message: " + itemShowFewInMessageFlag + "\n" +
               "Shown fields: \n" + shownFields + "\n" +
               "Applied filters: " + appliedFilters;
    }
}
