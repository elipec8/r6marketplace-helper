package github.ricemonger.utilspostgresschema.id_entities.user;

import github.ricemonger.utilspostgresschema.id_entities.item.IdItemEntity;
import github.ricemonger.utilspostgresschema.ids.user.TradeByItemIdManagerEntityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "trade_manager_by_item_id")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TradeByItemIdManagerEntityId.class)
public class IdTradeByItemIdManagerEntity {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private IdUserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private IdItemEntity item;

    public String getItemId_() {
        return item.getItemId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdTradeByItemIdManagerEntity tradeByItemIdManagerEntity)) {
            return false;
        }
        return Objects.equals(user, tradeByItemIdManagerEntity.user) &&
               Objects.equals(item, tradeByItemIdManagerEntity.item);
    }
}
