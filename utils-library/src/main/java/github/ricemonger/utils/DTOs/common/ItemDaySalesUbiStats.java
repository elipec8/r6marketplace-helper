package github.ricemonger.utils.DTOs.common;

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
    private Integer lowestPrice;
    private Integer averagePrice;
    private Integer highestPrice;
    private Integer itemsCount;
}
