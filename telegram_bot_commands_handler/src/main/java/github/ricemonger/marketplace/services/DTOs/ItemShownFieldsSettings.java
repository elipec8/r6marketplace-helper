package github.ricemonger.marketplace.services.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemShownFieldsSettings {
    private Boolean itemShowNameFlag;
    private Boolean itemShowItemTypeFlag;
    private Boolean itemShowMaxBuyPrice;
    private Boolean itemShowBuyOrdersCountFlag;
    private Boolean itemShowMinSellPriceFlag;
    private Boolean itemsShowSellOrdersCountFlag;
    private Boolean itemShowPictureFlag;

    public int getActiveFieldsCount() {
        int count = 1;
        if (itemShowNameFlag != null && itemShowNameFlag) count++;
        if (itemShowItemTypeFlag != null && itemShowItemTypeFlag) count++;
        if (itemShowMaxBuyPrice != null && itemShowMaxBuyPrice) count++;
        if (itemShowBuyOrdersCountFlag != null && itemShowBuyOrdersCountFlag) count++;
        if (itemShowMinSellPriceFlag != null && itemShowMinSellPriceFlag) count++;
        if (itemsShowSellOrdersCountFlag != null && itemsShowSellOrdersCountFlag) count++;
        if (itemShowPictureFlag != null && itemShowPictureFlag) count++;
        return count;
    }

    public String toString() {
        return "Show Item Name: " + itemShowNameFlag + "\n" +
               "Show Item Type: " + itemShowItemTypeFlag + "\n" +
               "Show Max Buy Price: " + itemShowMaxBuyPrice + "\n" +
               "Show Buy Orders Count: " + itemShowBuyOrdersCountFlag + "\n" +
               "Show Min Sell Price: " + itemShowMinSellPriceFlag + "\n" +
               "Show Sell Orders Count: " + itemsShowSellOrdersCountFlag + "\n" +
               "Show Item Picture: " + itemShowPictureFlag + "\n";
    }
}
