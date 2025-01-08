package github.ricemonger.item_trade_stats_calculator.postgres.dto_projections;


import java.time.LocalDate;

public interface ItemDaySalesUbiStatsDtoProjectionI {
    public String getItemId();

    public void setItemId(String itemId);

    public LocalDate getDate();

    public void setDate(LocalDate date);

    public Integer getLowestPrice();

    public void setLowestPrice(Integer lowestPrice);

    public Integer getAveragePrice();

    public void setAveragePrice(Integer averagePrice);

    public Integer getHighestPrice();

    public void setHighestPrice(Integer highestPrice);

    public Integer getItemsCount();

    public void setItemsCount(Integer itemsCount);
}
