package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemShownFieldsSettings {
    private boolean itemShowNameFlag;
    private boolean itemShowItemTypeFlag;
    private boolean itemShowMaxBuyPrice;
    private boolean itemShowBuyOrdersCountFlag;
    private boolean itemShowMinSellPriceFlag;
    private boolean itemsShowSellOrdersCountFlag;
    private boolean itemShowPictureFlag;

    public int getActiveFieldsCount() {
        int count = 1;
        if (itemShowNameFlag) count++;
        if (itemShowItemTypeFlag) count++;
        if (itemShowMaxBuyPrice) count++;
        if (itemShowBuyOrdersCountFlag) count++;
        if (itemShowMinSellPriceFlag) count++;
        if (itemsShowSellOrdersCountFlag) count++;
        if (itemShowPictureFlag) count++;
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
