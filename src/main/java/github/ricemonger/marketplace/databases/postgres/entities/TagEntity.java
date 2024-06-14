package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.enums.TagGroup;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    @Id
    private String value;
    private String name;
    private TagGroup tagGroup;
}
