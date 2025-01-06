package github.ricemonger.configs_fetcher.services.abstractions;

import github.ricemonger.utils.DTOs.common.Tag;

import java.util.Collection;

public interface TagDatabaseService {
    void saveAll(Collection<Tag> tags);
}
