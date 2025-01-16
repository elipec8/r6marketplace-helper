package github.ricemonger.item_stats_fetcher.databases.postgres.custom_entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSaleEntityId implements Serializable {
    private ItemMainFieldsEntity item;
    private LocalDateTime soldAt;
}
