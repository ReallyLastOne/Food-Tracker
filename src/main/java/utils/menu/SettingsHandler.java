package utils.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.ViewUtils;
import utils.spring.SpringFXMLLoader;

import static utils.ViewUtils.updateCSS;

public interface SettingsHandler {
    default void openSettings(boolean darkMode) {
        Parent root = (Parent) new SpringFXMLLoader().load("/SettingsView.fxml");
        Stage settingsWindow = ViewUtils.getStandardStage("Settings");
        Scene scene = new Scene(root, 300, 400);
        settingsWindow.setScene(scene);
        updateCSS(root, darkMode);
        settingsWindow.showAndWait();
    }
}
