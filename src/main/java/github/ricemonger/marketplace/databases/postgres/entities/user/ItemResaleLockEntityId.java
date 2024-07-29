package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLockEntityId {
    private UbiAccountEntity ubiAccount;
    private String itemId;

    public int hashCode(){
        return ubiAccount.getUser().getId().hashCode() + itemId.hashCode();
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof ItemResaleLockEntityId itemResaleLockEntityId)){
            return false;
        }
        return itemResaleLockEntityId.ubiAccount.getUser().getId().equals(ubiAccount.getUser().getId()) && itemResaleLockEntityId.itemId.equals(itemId);
    }
}
