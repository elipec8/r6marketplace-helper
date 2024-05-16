package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("ItemSaleHistory")
@Data
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
