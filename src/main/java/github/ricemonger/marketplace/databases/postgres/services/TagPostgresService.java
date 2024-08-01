package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.dtos.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TagPostgresService implements TagDatabaseService {

    private final TagPostgresRepository tagRepository;

    @Override
    public void saveAll(Collection<Tag> tags) {
        tagRepository.saveAll(tags.stream().map(TagEntity::new).toList());
    }

    @Override
    public Collection<Tag> findAllByNames(Collection<String> tagNames) {
        return tagRepository.findAllByNames(tagNames).stream().map(TagEntity::toTag).toList();
    }

    @Override
    public Collection<Tag> findAll() {
        return tagRepository.findAll().stream().map(TagEntity::toTag).toList();
    }
}
