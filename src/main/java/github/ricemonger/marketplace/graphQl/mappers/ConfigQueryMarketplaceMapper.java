package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.TagGroup;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.Tags;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.Type;
import github.ricemonger.utils.enums.ItemType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class ConfigQueryMarketplaceMapper {

    public Collection<github.ricemonger.utils.dtos.Tag> mapTags(Marketplace marketplace) {
        List<Tags> tags = marketplace.getTags();
        List<TagGroup> tagGroups = marketplace.getTagGroups();

        List<github.ricemonger.utils.dtos.Tag> result = new ArrayList<>();

        for (Tags tag : tags) {
            github.ricemonger.utils.dtos.Tag resultTag = new github.ricemonger.utils.dtos.Tag();
            resultTag.setValue(tag.getValue());
            resultTag.setName(tag.getDisplayName());
            result.add(resultTag);
        }

        for (github.ricemonger.utils.dtos.Tag tag : result) {
            for (TagGroup group : tagGroups) {
                if (group.getValues().contains(tag.getValue())) {
                    try {
                        tag.setTagGroup(github.ricemonger.utils.enums.TagGroup.valueOf(group.getDisplayName().replace(" ", "_")));
                    } catch (IllegalArgumentException e) {
                        log.error("Tag group not found: " + group.getDisplayName());
                        tag.setTagGroup(github.ricemonger.utils.enums.TagGroup.Unknown);
                    }
                }
            }
        }
        return result;
    }

    public void checkItemTypes(Marketplace marketplace) {
        List<Type> types = marketplace.getTypes();

        for(Type type : types){
            try{
                ItemType.valueOf(type.getValue());
            }
            catch(IllegalArgumentException e){
                log.error("Item type not found: " + type.getValue());
            }
        }
    }
}
