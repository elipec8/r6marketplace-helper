package github.ricemonger.marketplace.databases.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemShowSettingsProjection {
    private Integer itemShowMessagesLimit;
    private Boolean itemShowFewInMessageFlag;
    private Boolean itemShowNameFlag;
    private Boolean itemShowItemTypeFlag;
    private Boolean itemShowMaxBuyPrice;
    private Boolean itemShowBuyOrdersCountFlag;
    private Boolean itemShowMinSellPriceFlag;
    private Boolean itemsShowSellOrdersCountFlag;
    private Boolean itemShowPictureFlag;
}
