package github.ricemonger.utils.DTOs.items;

import java.time.LocalDateTime;

public interface SoldItemDetails extends ItemIdFieldI {
    LocalDateTime getLastSoldAt();

    void setLastSoldAt(LocalDateTime lastSoldAt);

    Integer getLastSoldPrice();

    void setLastSoldPrice(Integer lastSoldPrice);
}
