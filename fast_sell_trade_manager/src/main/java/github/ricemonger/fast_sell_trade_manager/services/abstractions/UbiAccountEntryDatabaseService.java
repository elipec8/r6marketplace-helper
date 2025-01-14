package github.ricemonger.fast_sell_trade_manager.services.abstractions;


import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;

import java.util.List;

public interface UbiAccountEntryDatabaseService {
    FastSellManagedUser getFastSellManagedUserById(Long fastSellManagedUserId, String email);

    List<ItemMedianPriceAndRarity> getOwnedItemsMedianPriceAndRarity(String ubiProfileId);
}
