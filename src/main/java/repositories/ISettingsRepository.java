package repositories;

import model.Settings;

@org.springframework.stereotype.Repository
public interface ISettingsRepository extends Repository {
    Settings getSettingsById(int id);
    Settings saveSettings(Settings settings);
    void deleteSettings(Settings settings);

}
