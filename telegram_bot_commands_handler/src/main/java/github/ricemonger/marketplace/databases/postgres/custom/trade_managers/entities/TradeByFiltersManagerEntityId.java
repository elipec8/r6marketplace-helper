package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByFiltersManagerEntityId {
    private UserEntity user;
    private String name;
}
