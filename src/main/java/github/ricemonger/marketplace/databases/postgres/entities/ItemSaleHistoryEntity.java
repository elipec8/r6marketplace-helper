package github.ricemonger.marketplace.databases.postgres.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "item_sale_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleHistoryEntity {
    @Id
    private String itemId;

    private int monthAveragePrice;
    private int monthMedianPrice;
    private int monthMaxPrice;
    private int monthMinPrice;
    private int monthSalesPerDay;
    private int monthLowPriceSalesPerDay;
    private int monthHighPriceSalesPerDay;

    private int dayAveragePrice;
    private int dayMedianPrice;
    private int dayMaxPrice;
    private int dayMinPrice;
    private int daySales;
    private int dayLowPriceSales;
    private int dayHighPriceSales;
}
