package github.ricemonger.marketplace.databases.postgres.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "planned_trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramTradeEntity {

    @Id
    private String tradeId;
}
