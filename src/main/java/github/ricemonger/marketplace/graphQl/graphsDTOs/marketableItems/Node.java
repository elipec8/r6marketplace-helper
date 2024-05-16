package github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems;

import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.MarketData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    private Item item;

    private MarketData marketData;

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Node node)){
            return false;
        }

        if(item == null && node.item == null) {
            return true;
        }
        else if(item == null || node.item == null) {
            return false;
        }
        else {
            return Objects.equals(item.getId(), node.item.getId());
        }
    }

    public int hashCode(){
        if(item == null || item.getId() == null){
            return 0;
        }
        return item.getId().hashCode();
    }
}

