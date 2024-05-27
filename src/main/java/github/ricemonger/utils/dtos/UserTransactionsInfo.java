package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionsInfo {
    private int buyResolvedTransactionCount;
    private int buyActiveTransactionCount;

    private int sellResolvedTransactionCount;
    private int sellActiveTransactionCount;
}
