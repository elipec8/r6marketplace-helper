package github.ricemonger.item_trade_stats_calculator.postgres.dto_projections;

import java.time.LocalDateTime;

public interface ItemSaleProjectionI {
    public String getItemId();

    public void setItemId(String itemId);

    public LocalDateTime getSoldAt();

    public void setSoldAt(LocalDateTime soldAt);

    public Integer getPrice();

    public void setPrice(Integer price);
}
