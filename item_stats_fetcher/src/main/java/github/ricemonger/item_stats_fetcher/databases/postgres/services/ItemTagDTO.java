package github.ricemonger.item_stats_fetcher.databases.postgres.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTagDTO {
    private String itemId;
    private String tagValue;
}
