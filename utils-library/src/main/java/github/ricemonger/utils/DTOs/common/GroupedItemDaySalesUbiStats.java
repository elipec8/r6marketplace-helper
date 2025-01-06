package github.ricemonger.utils.DTOs.common;

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

    private List<ItemDaySalesUbiStats> daySales = new ArrayList<>();
}

