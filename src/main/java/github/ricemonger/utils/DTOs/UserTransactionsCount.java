package github.ricemonger.utils.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionsCount {
    private int buyResolvedTransactionCount;
    private int buyActiveTransactionCount;

    private int sellResolvedTransactionCount;
    private int sellActiveTransactionCount;
}
