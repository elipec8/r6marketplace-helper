package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "item_filter")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemFilterEntityId.class)
public class ItemFilterEntity {
    @MapsId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Id
    @Column(name = "name")
    private String name;

    @Override
    public int hashCode() {
        return Objects.hash(user, name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemFilterEntity itemFilterEntity)) {
            return false;
        }
        return Objects.equals(this.user, itemFilterEntity.user) &&
               Objects.equals(this.name, itemFilterEntity.name);
    }
}
