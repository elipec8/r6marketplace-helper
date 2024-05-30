package github.ricemonger.utils.dtos;

import java.util.Date;

public interface SoldItemDetails {
    String getItemId();

    void setItemId(String itemId);

    Date getLastSoldAt();

    void setLastSoldAt(Date lastSoldAt);

    int getLastSoldPrice();

    void setLastSoldPrice(int lastSoldPrice);
}
