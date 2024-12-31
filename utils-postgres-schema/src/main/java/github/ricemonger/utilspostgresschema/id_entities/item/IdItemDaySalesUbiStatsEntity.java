package github.ricemonger.utilspostgresschema.id_entities.item;

import github.ricemonger.utilspostgresschema.ids.item.ItemDaySalesUbiStatsEntityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "item_day_sales_ubi_stats")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemDaySalesUbiStatsEntityId.class)
public abstract class IdItemDaySalesUbiStatsEntity {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private IdItemEntity item;

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Override
    public int hashCode() {
        return Objects.hash(item, date);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IdItemDaySalesUbiStatsEntity itemDaySalesUbiStatsEntity)) {
            return false;
        }
        return Objects.equals(this.item, itemDaySalesUbiStatsEntity.item) &&
               Objects.equals(this.date, itemDaySalesUbiStatsEntity.date);
    }
}
