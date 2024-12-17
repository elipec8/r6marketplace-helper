package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.DTOs.UbiTrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalItem extends Item {
    private boolean isOwned;
    private List<UbiTrade> trades;

    @Override
    public String toString() {
        return "PersonalItem{" +
               super.toString() +
               "isOwned=" + isOwned +
               ", trades=" + trades +
               '}';
    }
}
