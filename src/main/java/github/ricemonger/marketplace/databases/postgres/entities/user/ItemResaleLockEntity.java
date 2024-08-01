package github.ricemonger.marketplace.databases.postgres.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Entity(name = "item_resale_lock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resale_lock_sequence")
    @SequenceGenerator(name = "resale_lock_sequence", sequenceName = "resale_lock_sequence", allocationSize = 1)
    private Long id;

    private String itemId;

    private Date expiresAt;
}
