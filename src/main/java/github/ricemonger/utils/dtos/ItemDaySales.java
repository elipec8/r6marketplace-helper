package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySales {
    private Date date;
    private int lowestPrice;
    private int averagePrice;
    private int highestPrice;
    private int itemsCount;
    private int averageNoEdgesPrice;
}
