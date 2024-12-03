package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.DTOs.UbiTrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalItem extends Item {
    private boolean isOwned;
    private List<UbiTrade> trades;

    @Override
    public String toString() {
        return "PersonalItem{" + "\n" +
               "ItemId" + super.getItemId() + "\n" +
               "AssetUrl" + super.getAssetUrl() + "\n" +
               "Name" + super.getName() + "\n" +
               "Tags" + super.getTags() + "\n" +
               "Rarity" + super.getRarity() + "\n" +
               "Type" + super.getType() + "\n" +
               "MaxBuyPrice" + super.getMaxBuyPrice() + "\n" +
               "BuyOrdersCount" + super.getBuyOrdersCount() + "\n" +
               "MinSellPrice" + super.getMinSellPrice() + "\n" +
               "SellOrdersCount" + super.getSellOrdersCount() + "\n" +
               "LastSoldAt" + super.getLastSoldAt() + "\n" +
               "LastSoldPrice" + super.getLastSoldPrice() + "\n" +
               "isOwned" + isOwned + "\n" +
               "trades" + trades + "\n" +
               '}';
    }
}
