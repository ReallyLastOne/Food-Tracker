package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Settings;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.ISettingsRepository;

import java.net.URL;
import java.util.ResourceBundle;

import static utils.ViewUtils.updateCSS;

@Component
public class SettingsController implements Initializable {
    @FXML
    private Text message;
    @FXML
    private TextField carbsPercentInput;
    @FXML
    private TextField proteinsPercentInput;
    @FXML
    private TextField fatsPercentInput;

    @FXML
    private TextField dailyCaloriesInput;
    @FXML
    private ToggleSwitch darkModeSwitcher;
    @FXML
    private Button applySettingsButton;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @Autowired
    ISettingsRepository settingsRepository;
    Settings currentSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentSettings = settingsRepository.getSettingsById(0);
        displayActualSettings();
    }

    @FXML
    public void switchDarkMode() {
        currentSettings.setDarkMode(!currentSettings.isDarkMode());
        updateCSS(applySettingsButton, currentSettings.isDarkMode());
    }

    @FXML
    public void applySettingsClicked() {
        saveSettings();
    }

    @FXML
    public void okButtonClicked(){
        applySettingsClicked();
        Stage st = (Stage) applySettingsButton.getScene().getWindow();
        st.close();
    }

    @FXML
    public void cancelButtonClicked() {
        Stage st = (Stage) cancelButton.getScene().getWindow();
        st.close();
    }

    private void saveSettings() {
        try {
            currentSettings.setDailyCalories(Integer.parseInt(dailyCaloriesInput.getText()));

            if (Integer.parseInt(carbsPercentInput.getText()) + Integer.parseInt(fatsPercentInput.getText()) +
                    Integer.parseInt(proteinsPercentInput.getText()) == 100) {
                currentSettings.setDailyCarbsPercent(Integer.parseInt(carbsPercentInput.getText()));
                currentSettings.setDailyFatsPercent(Integer.parseInt(fatsPercentInput.getText()));
                currentSettings.setDailyProteinsPercent(Integer.parseInt(proteinsPercentInput.getText()));
                settingsRepository.saveSettings(currentSettings);

                message.setStyle("-fx-font-size: 18;");
                message.setText("Settings saved successfully!");
                message.setFill(Color.LIGHTGREEN);
            } else {
                throw new IllegalArgumentException("Percents must sum up to 100.");
            }
        } catch (NumberFormatException e) {
            message.setStyle("-fx-font-size: 18;");
            message.setText("Wrong input.");
            message.setFill(Color.DARKRED);
        } catch (Exception e) {
            message.setStyle("-fx-font-size: 18;");
            message.setText(e.getMessage());
            message.setFill(Color.DARKRED);
        }
    }

    private void displayActualSettings() {
        if(currentSettings.isDarkMode()) darkModeSwitcher.fire();

        dailyCaloriesInput.setText(String.valueOf(currentSettings.getDailyCalories()));

        dailyCaloriesInput.setText(String.valueOf(currentSettings.getDailyCalories()));
        carbsPercentInput.setText(String.valueOf(currentSettings.getDailyCarbsPercent()));
        proteinsPercentInput.setText(String.valueOf(currentSettings.getDailyProteinsPercent()));
        fatsPercentInput.setText(String.valueOf(currentSettings.getDailyFatsPercent()));
    }
}
