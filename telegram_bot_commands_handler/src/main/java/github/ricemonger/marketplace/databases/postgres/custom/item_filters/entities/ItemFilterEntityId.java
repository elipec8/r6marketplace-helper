package github.ricemonger.marketplace.databases.postgres.custom.item_filters.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFilterEntityId {
    private UserEntity user;
    private String name;
}
