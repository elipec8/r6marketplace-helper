package github.ricemonger.marketplace.updateFetcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    String id;

    String name;

    List<String> tags;

    String type;

}
