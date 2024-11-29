package github.ricemonger.utils.DTOs.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesUbiStats {
    private String itemId;
    private LocalDate date;
    private int lowestPrice;
    private int averagePrice;
    private int highestPrice;
    private int itemsCount;
}
