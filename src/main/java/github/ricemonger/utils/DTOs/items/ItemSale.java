package github.ricemonger.utils.DTOs.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSale implements SoldItemDetails {
    private String itemId;
    private LocalDateTime soldAt;
    private int price;

    @Override
    public LocalDateTime getLastSoldAt() {
        return soldAt;
    }

    @Override
    public void setLastSoldAt(LocalDateTime lastSoldAt) {
        this.soldAt = lastSoldAt;
    }

    @Override
    public Integer getLastSoldPrice() {
        return price;
    }

    @Override
    public void setLastSoldPrice(Integer lastSoldPrice) {
        this.price = lastSoldPrice;
    }
}
