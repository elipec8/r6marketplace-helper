package github.ricemonger.utils.DTOs.common;

import java.time.LocalDateTime;

public interface SoldItemDetails extends ItemIdFieldI {
    LocalDateTime getLastSoldAt();

    void setLastSoldAt(LocalDateTime lastSoldAt);

    Integer getLastSoldPrice();

    void setLastSoldPrice(Integer lastSoldPrice);
}
