package github.ricemonger.utils.DTOs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigResolvedTransactionPeriod {
    private Integer buyResolvedTransactionPeriod;
    private Integer sellResolvedTransactionPeriod;
}
