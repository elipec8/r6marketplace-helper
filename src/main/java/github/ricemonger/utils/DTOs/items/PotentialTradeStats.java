package github.ricemonger.utils.DTOs.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PotentialTradeStats {
    private Integer price;
    private Integer prognosedTradeSuccessMinutes;
    private Long tradePriority;
}
