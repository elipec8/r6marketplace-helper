package github.ricemonger.utils.DTOs.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLock {
    private String ubiProfileId;
    private String itemId;
    private Date expiresAt;
}
