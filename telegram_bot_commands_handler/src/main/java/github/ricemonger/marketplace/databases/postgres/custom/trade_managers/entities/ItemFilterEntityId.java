package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities;

import github.ricemonger.marketplace.databases.postgres.custom.settings.entities.UserEntity;
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
