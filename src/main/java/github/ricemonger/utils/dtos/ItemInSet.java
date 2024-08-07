package github.ricemonger.utils.dtos;

public class ItemInSet extends Item {
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item item)) {
            return false;
        }
        if (item.getItemId() == null || this.getItemId() == null) {
            return false;
        }
        return item.getItemId().equals(this.getItemId());
    }

    public int hashCode() {
        return this.getItemId().hashCode();
    }
}
