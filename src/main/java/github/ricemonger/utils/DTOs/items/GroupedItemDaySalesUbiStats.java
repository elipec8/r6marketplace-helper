package github.ricemonger.utils.DTOs.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedItemDaySalesUbiStats {
    private String itemId;

    private List<ItemDaySalesUbiStatsEntityDTO> daySales = new ArrayList<>();
}

