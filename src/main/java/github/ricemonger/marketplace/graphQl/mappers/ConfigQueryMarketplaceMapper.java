package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.TagGroup;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.Tags;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.Type;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.exceptions.GraphQlConfigQueryMarketplaceMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ConfigQueryMarketplaceMapper {

    public List<github.ricemonger.utils.dtos.Tag> mapTags(Marketplace marketplace) throws GraphQlConfigQueryMarketplaceMappingException {

        if (marketplace == null) {
            throw new GraphQlConfigQueryMarketplaceMappingException("Marketplace is null");
        }

        List<Tags> tags = marketplace.getTags();

        if (tags == null) {
            throw new GraphQlConfigQueryMarketplaceMappingException("Tags is null");
        }

        List<TagGroup> tagGroups = marketplace.getTagGroups();

        if (tagGroups == null) {
            throw new GraphQlConfigQueryMarketplaceMappingException("TagGroups is null");
        }


        List<github.ricemonger.utils.dtos.Tag> result = new ArrayList<>();

        for (Tags tag : tags) {
            if (tag == null || tag.getValue() == null || tag.getDisplayName() == null) {
                throw new GraphQlConfigQueryMarketplaceMappingException("Tag value or display name is null, in tags-" + tags);
            }
            github.ricemonger.utils.dtos.Tag resultTag = new github.ricemonger.utils.dtos.Tag();
            resultTag.setValue(tag.getValue());
            resultTag.setName(tag.getDisplayName());

            result.add(resultTag);
        }

        for (github.ricemonger.utils.dtos.Tag tag : result) {
            for (TagGroup group : tagGroups) {
                if(group == null || group.getDisplayName() == null || group.getValues() == null) {
                    throw new GraphQlConfigQueryMarketplaceMappingException("Tag group display name or values is null, in tagGroups-" + tagGroups);
                }
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

    public void checkItemTypes(Marketplace marketplace) throws GraphQlConfigQueryMarketplaceMappingException {
        if (marketplace == null) {
            throw new GraphQlConfigQueryMarketplaceMappingException("Marketplace is null");
        }

        List<Type> types = marketplace.getTypes();

        if (types == null) {
            throw new GraphQlConfigQueryMarketplaceMappingException("Types is null");
        }

        for (Type type : types) {
            try {
                ItemType.valueOf(type.getValue());
            } catch (IllegalArgumentException e) {
                log.error("Item type not found: " + type.getValue());
            } catch (NullPointerException e) {
                throw new GraphQlConfigQueryMarketplaceMappingException("Item type is null in types-" + types);
            }
        }
    }
}
