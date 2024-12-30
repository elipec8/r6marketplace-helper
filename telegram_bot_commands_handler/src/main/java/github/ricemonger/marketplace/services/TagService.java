package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.enums.TagGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDatabaseService tagDatabaseService;

    public List<Tag> getTagsByNames(Collection<String> tagNames) {
        return tagDatabaseService.findAllByNames(tagNames);
    }

    public List<Tag> getAllTagsByTagGroup(TagGroup tagGroup) {
        return  tagDatabaseService.findAllByTagGroup(tagGroup);
    }
}
