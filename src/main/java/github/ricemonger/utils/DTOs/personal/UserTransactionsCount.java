package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionsCount {
    private Integer buyResolvedTransactionCount;
    private Integer buyActiveTransactionCount;

    private Integer sellResolvedTransactionCount;
    private Integer sellActiveTransactionCount;
}
