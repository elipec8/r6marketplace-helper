package github.ricemonger.utils.DTOs.common;

import github.ricemonger.utils.enums.TagGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private String value;
    private String name;
    private TagGroup tagGroup;
}
