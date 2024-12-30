package github.ricemonger.marketplace.databases.postgres.custom.settings.service;

import github.ricemonger.marketplace.databases.postgres.custom.settings.entities.UserEntity;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsMapper {
    public ItemShowSettings createItemShowSettingsDTO(UserEntity userSettingsEntity) {

    }

    public TradeManagersSettings createTradeManagersSettingsDTO(UserEntity userSettingsEntity) {

    }
}
