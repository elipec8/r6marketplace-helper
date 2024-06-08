package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.databases.postgres.mappers.TagPostgresMapper;
import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.dtos.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDatabaseService tagDatabaseService;

    public void saveAll(Collection<Tag> tags) {
        tagDatabaseService.saveAll(tags);
    }

    public Collection<Tag> getAllTags() {
        return tagDatabaseService.findAll();
    }

    public String getValueByName(String name) {
        return tagDatabaseService.findByName(name).getValue();
    }

    public String getNameByValue(String value) {
        return tagDatabaseService.findByValue(value).getName();
    }
}
