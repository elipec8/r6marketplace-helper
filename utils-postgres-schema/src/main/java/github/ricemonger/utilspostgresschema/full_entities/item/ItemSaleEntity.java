package github.ricemonger.utilspostgresschema.full_entities.item;

import github.ricemonger.utilspostgresschema.id_entities.item.IdItemSaleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleEntity extends IdItemSaleEntity {
    @Column(name = "price")
    private Integer price;
}
