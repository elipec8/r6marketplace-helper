package github.ricemonger.utils.DTOs.items;

public interface ItemHistoryFieldsI extends ItemIdFieldI {
    Integer getMonthAveragePrice();

    void setMonthAveragePrice(Integer monthAveragePrice);

    Integer getMonthMedianPrice();

    void setMonthMedianPrice(Integer monthMedianPrice);

    Integer getMonthMaxPrice();

    void setMonthMaxPrice(Integer monthMaxPrice);

    Integer getMonthMinPrice();

    void setMonthMinPrice(Integer monthMinPrice);

    Integer getMonthSalesPerDay();

    void setMonthSalesPerDay(Integer monthSalesPerDay);

    Integer getDayAveragePrice();

    void setDayAveragePrice(Integer dayAveragePrice);

    Integer getDayMedianPrice();

    void setDayMedianPrice(Integer dayMedianPrice);

    Integer getDayMaxPrice();

    void setDayMaxPrice(Integer dayMaxPrice);

    Integer getDayMinPrice();

    void setDayMinPrice(Integer dayMinPrice);

    Integer getDaySales();

    void setDaySales(Integer daySales);

    Integer getPriceToSellIn1Hour();

    void setPriceToSellIn1Hour(Integer priceToSellIn1Hour);

    Integer getPriceToSellIn6Hours();

    void setPriceToSellIn6Hours(Integer priceToSellIn6Hours);

    Integer getPriceToSellIn24Hours();

    void setPriceToSellIn24Hours(Integer priceToSellIn24Hours);

    Integer getPriceToSellIn168Hours();

    void setPriceToSellIn168Hours(Integer priceToSellIn168Hours);

    Integer getPriceToBuyIn1Hour();

    void setPriceToBuyIn1Hour(Integer priceToBuyIn1Hour);

    Integer getPriceToBuyIn6Hours();

    void setPriceToBuyIn6Hours(Integer priceToBuyIn6Hours);

    Integer getPriceToBuyIn24Hours();

    void setPriceToBuyIn24Hours(Integer priceToBuyIn24Hours);

    Integer getPriceToBuyIn168Hours();

    void setPriceToBuyIn168Hours(Integer priceToBuyIn168Hours);

    default void setHistoryFields(ItemHistoryFieldsI historyFieldsI) {
        this.setMonthAveragePrice(historyFieldsI.getMonthAveragePrice());
        this.setMonthMedianPrice(historyFieldsI.getMonthMedianPrice());
        this.setMonthMaxPrice(historyFieldsI.getMonthMaxPrice());
        this.setMonthMinPrice(historyFieldsI.getMonthMinPrice());
        this.setMonthSalesPerDay(historyFieldsI.getMonthSalesPerDay());

        this.setDayAveragePrice(historyFieldsI.getDayAveragePrice());
        this.setDayMedianPrice(historyFieldsI.getDayMedianPrice());
        this.setDayMaxPrice(historyFieldsI.getDayMaxPrice());
        this.setDayMinPrice(historyFieldsI.getDayMinPrice());
        this.setDaySales(historyFieldsI.getDaySales());

        this.setPriceToSellIn1Hour(historyFieldsI.getPriceToSellIn1Hour());
        this.setPriceToSellIn6Hours(historyFieldsI.getPriceToSellIn6Hours());
        this.setPriceToSellIn24Hours(historyFieldsI.getPriceToSellIn24Hours());
        this.setPriceToSellIn168Hours(historyFieldsI.getPriceToSellIn168Hours());

        this.setPriceToBuyIn1Hour(historyFieldsI.getPriceToBuyIn1Hour());
        this.setPriceToBuyIn6Hours(historyFieldsI.getPriceToBuyIn6Hours());
        this.setPriceToBuyIn24Hours(historyFieldsI.getPriceToBuyIn24Hours());
        this.setPriceToBuyIn168Hours(historyFieldsI.getPriceToBuyIn168Hours());
    }
}
