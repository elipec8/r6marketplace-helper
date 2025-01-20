package github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta;

import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.Nodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trades {
    private List<Nodes> nodes;
}
