package github.ricemonger.utils.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigResolvedTransactionPeriod {
    private int buyResolvedTransactionPeriod;
    private int sellResolvedTransactionPeriod;
}
