package github.ricemonger.trades_manager.postgres.entities.manageable_users;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFilterEntityId {
    private ManageableUserEntity user;
    private String name;
}
