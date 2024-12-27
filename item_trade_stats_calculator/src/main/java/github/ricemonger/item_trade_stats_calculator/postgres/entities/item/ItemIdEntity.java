package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemIdEntity {
    @Id
    private String itemId;
}
