package github.ricemonger.utils.DTOs.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLock {
    private String itemId;
    private LocalDateTime expiresAt;
}
