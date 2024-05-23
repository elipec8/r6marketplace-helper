package github.ricemonger.marketplace.databases.postgres.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "item_sale")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemSaleEntityId.class)
public class ItemSaleEntity {

    @Id
    private String itemId;

    @Id
    private Date soldAt;

    private int price;
}
