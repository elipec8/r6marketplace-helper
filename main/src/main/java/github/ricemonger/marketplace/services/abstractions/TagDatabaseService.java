package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.common.Tag;

import java.util.Collection;
import java.util.List;

public interface TagDatabaseService {
    List<Tag> findAllByNames(Collection<String> tagNames);

    List<Tag> findAll();
}
