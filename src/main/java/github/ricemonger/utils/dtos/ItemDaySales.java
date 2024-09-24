package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySales {
    private Date date;
    private int lowestPrice;
    private int averagePrice;
    private int highestPrice;
    private int itemsCount;
    private int averageNoEdgesPrice;

    @Override
    public int hashCode() {
        return (int)date.getTime() + lowestPrice + averagePrice + highestPrice + itemsCount + averageNoEdgesPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemDaySales other = (ItemDaySales) obj;
        if (this.date != other.date && (this.date == null || !(this.date.getTime() == other.date.getTime()))) {
            return false;
        }
        if (this.lowestPrice != other.lowestPrice) {
            return false;
        }
        if (this.averagePrice != other.averagePrice) {
            return false;
        }
        if (this.highestPrice != other.highestPrice) {
            return false;
        }
        if (this.itemsCount != other.itemsCount) {
            return false;
        }
        if (this.averageNoEdgesPrice != other.averageNoEdgesPrice) {
            return false;
        }
        return true;
    }
}
