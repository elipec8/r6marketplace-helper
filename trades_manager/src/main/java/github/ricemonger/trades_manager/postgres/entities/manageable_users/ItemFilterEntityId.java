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

    public ItemFilterEntityId(Long userId, String name) {
        this.user = new ManageableUserEntity(userId);
        this.name = name;
    }

    public long getUserId_() {
        return user.getId();
    }

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
        if (this.hashCode() != itemFilterEntityId.hashCode()) {
            return false;
        }
        return itemFilterEntityId.user.getId().equals(user.getId()) && itemFilterEntityId.name.equals(name);
    }
}
