package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSale implements SoldItemDetails {
    private String itemId;
    private Date soldAt;
    private int price;

    @Override
    public Date getLastSoldAt() {
        return soldAt;
    }

    @Override
    public void setLastSoldAt(Date lastSoldAt) {
        this.soldAt = lastSoldAt;
    }

    @Override
    public int getLastSoldPrice() {
        return price;
    }

    @Override
    public void setLastSoldPrice(int lastSoldPrice) {
        this.price = lastSoldPrice;
    }
}
