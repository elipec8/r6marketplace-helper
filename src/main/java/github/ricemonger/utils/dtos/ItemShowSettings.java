package github.ricemonger.utils.dtos;

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
    private boolean itemShowFewInMessageFlag;

    private ItemShownFieldsSettings shownFieldsSettings = new ItemShownFieldsSettings();

    private Collection<ItemFilter> itemShowAppliedFilters = new ArrayList<>();


    public boolean isItemShowNameFlag() {
        return shownFieldsSettings.isItemShowNameFlag();
    }


    public void setItemShowNameFlag(boolean itemShowNameFlag) {
        shownFieldsSettings.setItemShowNameFlag(itemShowNameFlag);
    }


    public boolean isItemShowItemTypeFlag() {
        return shownFieldsSettings.isItemShowItemTypeFlag();
    }


    public void setItemShowItemTypeFlag(boolean itemShowItemTypeFlag) {
        shownFieldsSettings.setItemShowItemTypeFlag(itemShowItemTypeFlag);
    }

    public boolean isItemShowMaxBuyPrice() {
        return shownFieldsSettings.isItemShowMaxBuyPrice();
    }


    public void setItemShowMaxBuyPrice(boolean itemShowMaxBuyPrice) {
        shownFieldsSettings.setItemShowMaxBuyPrice(itemShowMaxBuyPrice);
    }


    public boolean isItemShowBuyOrdersCountFlag() {
        return shownFieldsSettings.isItemShowBuyOrdersCountFlag();
    }


    public void setItemShowBuyOrdersCountFlag(boolean itemShowBuyOrdersCountFlag) {
        shownFieldsSettings.setItemShowBuyOrdersCountFlag(itemShowBuyOrdersCountFlag);
    }


    public boolean isItemShowMinSellPriceFlag() {
        return shownFieldsSettings.isItemShowMinSellPriceFlag();
    }


    public void setItemShowMinSellPriceFlag(boolean itemShowMinSellPriceFlag) {
        shownFieldsSettings.setItemShowMinSellPriceFlag(itemShowMinSellPriceFlag);
    }


    public boolean isItemsShowSellOrdersCountFlag() {
        return shownFieldsSettings.isItemsShowSellOrdersCountFlag();
    }


    public void setItemsShowSellOrdersCountFlag(boolean itemsShowSellOrdersCountFlag) {
        shownFieldsSettings.setItemsShowSellOrdersCountFlag(itemsShowSellOrdersCountFlag);
    }


    public boolean isItemShowPictureFlag() {
        return shownFieldsSettings.isItemShowPictureFlag();
    }


    public void setItemShowPictureFlag(boolean itemShowPictureFlag) {
        shownFieldsSettings.setItemShowPictureFlag(itemShowPictureFlag);
    }

    public int getActiveFieldsCount() {
        return shownFieldsSettings.getActiveFieldsCount();
    }

    public String toString(){
        return "Messages limit: " + itemShowMessagesLimit + "\n" +
                "Few items in message: " + itemShowFewInMessageFlag + "\n" +
                "Shown fields: \n" + shownFieldsSettings.toString() + "\n" +
                "Applied filters: " + itemShowAppliedFilters.stream().map(ItemFilter::getName).reduce((s, s2) -> s + "," + s2).orElse("");
    }
}
