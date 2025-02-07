package github.ricemonger.utils.DTOs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PotentialTradeStats {
    private Integer price;
    private Integer time;

    public boolean isValid() {
        return price != null && time != null;
    }
}
