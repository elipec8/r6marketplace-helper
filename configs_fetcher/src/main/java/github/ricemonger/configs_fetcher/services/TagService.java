package github.ricemonger.configs_fetcher.services;


import github.ricemonger.configs_fetcher.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.DTOs.common.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDatabaseService tagDatabaseService;

    public void saveAllTags(Collection<Tag> tags) {
        tagDatabaseService.saveAll(tags);
    }
}
