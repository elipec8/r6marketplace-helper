package github.ricemonger.utils.DTOs.personal;

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

    public String toString() {
        return "Messages limit: " + itemShowMessagesLimit + "\n" +
               "Few items in message: " + itemShowFewInMessageFlag + "\n" +
               "Shown fields: \n" + shownFieldsSettings.toString() + "\n" +
               "Applied filters: " + itemShowAppliedFilters.stream().map(ItemFilter::getName).reduce((s, s2) -> s + "," + s2).orElse("");
    }
}
