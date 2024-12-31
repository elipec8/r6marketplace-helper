package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utilspostgresschema.id_entities.user.IdTelegramUserEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdTelegramUserInputEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserEntity extends IdTelegramUserEntity {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "telegramUser")
    private List<TelegramUserInputEntity> telegramUserInputs = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "input_state")
    private InputState inputState = InputState.BASE;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "input_group")
    private InputGroup inputGroup = InputGroup.BASE;

    @Column(name = "item_show_messages_limit")
    private Integer itemShowMessagesLimit = 50;
    @Column(name = "item_show_few_in_message_flag")
    private Boolean itemShowFewInMessageFlag = false;
}
