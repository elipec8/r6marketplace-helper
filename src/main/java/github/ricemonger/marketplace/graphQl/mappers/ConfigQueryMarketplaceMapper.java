package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.TagGroups;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.Tags;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.Types;
import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class ConfigQueryMarketplaceMapper {

    public Collection<Tag> mapTags(Marketplace marketplace) {
        List<Tags> tags = marketplace.getTags();
        List<TagGroups> tagGroups = marketplace.getTagGroups();

        List<Tag> result = new ArrayList<>();

        for (Tags tag : tags) {
            Tag resultTag = new Tag();
            resultTag.setValue(tag.getValue());
            resultTag.setName(tag.getDisplayName());
            result.add(resultTag);
        }

        for (Tag tag : result) {
            for (TagGroups group : tagGroups) {
                if (group.getValues().contains(tag.getValue())) {
                    try {
                        tag.setTagGroup(TagGroup.valueOf(group.getDisplayName().replace(" ", "_")));
                    } catch (IllegalArgumentException e) {
                        log.error("Tag group not found: " + group.getDisplayName());
                        tag.setTagGroup(TagGroup.Unknown);
                    }
                }
            }
        }
        return result;
    }

    public void checkItemTypes(Marketplace marketplace) {
        List<Types> types = marketplace.getTypes();

        for(Types type : types){
            try{
                ItemType.valueOf(type.getValue());
            }
            catch(IllegalArgumentException e){
                log.error("Item type not found: " + type.getValue());
            }
        }
    }
}
