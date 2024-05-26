package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.enums.ItemType;

import java.util.Date;
import java.util.List;

public interface Item {

    String getItemId();

    String getAssetUrl();

    String getName();

    ItemType getType();

    int getMaxBuyPrice();

    int getBuyOrdersCount();

    int getMinSellPrice();

    int getSellOrdersCount();

    int getExpectedProfit();

    int getExpectedProfitPercentage();

    Date getLastSoldAt();

    int getLastSoldPrice();

}
