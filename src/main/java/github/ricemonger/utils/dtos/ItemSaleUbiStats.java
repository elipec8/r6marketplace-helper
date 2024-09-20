package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleUbiStats {
    private String itemId;

    private List<ItemDaySales> last30DaysSales = new ArrayList<>();
}

