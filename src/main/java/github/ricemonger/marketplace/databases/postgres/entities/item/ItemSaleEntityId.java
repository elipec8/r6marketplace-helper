package github.ricemonger.marketplace.databases.postgres.entities.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSaleEntityId implements Serializable {
    private String itemId;
    private Date soldAt;
}
