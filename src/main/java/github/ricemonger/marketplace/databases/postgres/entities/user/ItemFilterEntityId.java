package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFilterEntityId {
    private UserEntity user;
    private String name;

    public int hashCode() {
        return user.getId().hashCode() + name.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemFilterEntityId itemFilterEntityId)) {
            return false;
        }
        return itemFilterEntityId.user.getId().equals(user.getId()) && itemFilterEntityId.name.equals(name);
    }
}
