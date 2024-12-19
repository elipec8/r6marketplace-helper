package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_factories.item.TagEntityFactory;
import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.DTOs.items.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagPostgresService implements TagDatabaseService {

    private final TagPostgresRepository tagRepository;

    private final TagEntityFactory tagEntityFactory;

    @Override
    @Transactional
    public void saveAll(Collection<Tag> tags) {
        tagRepository.saveAll(tags.stream().map(tagEntityFactory::createEntity).toList());
    }

    @Override
    public List<Tag> findAllByNames(Collection<String> tagNames) {
        return tagRepository.findAllByNames(tagNames).stream().map(tagEntityFactory::createDTO).toList();
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll().stream().map(tagEntityFactory::createDTO).toList();
    }
}
