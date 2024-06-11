package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemShownFieldsSettings {
    private boolean itemShowNameFlag = true;
    private boolean itemShowItemTypeFlag = true;
    private boolean itemShowMaxBuyPrice = true;
    private boolean itemShowBuyOrdersCountFlag = true;
    private boolean itemShowMinSellPriceFlag = true;
    private boolean itemsShowSellOrdersCountFlag = true;
    private boolean itemShowPictureFlag = true;

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
