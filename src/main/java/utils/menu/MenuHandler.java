package utils.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ViewUtils;
import utils.spring.SpringFXMLLoader;

import static utils.ViewUtils.updateCSS;
/** Responsible for properly opening menu related stages.
 * */
public interface MenuHandler extends AddingEdiblesHandler, SettingsHandler {
    default void openAbout(boolean darkMode) {
        Parent root = (Parent) new SpringFXMLLoader().load("/AboutView.fxml");
        Stage aboutWindow = ViewUtils.getStandardStage("About");
        Scene scene = new Scene(root, 200, 200);
        aboutWindow.setScene(scene);
        updateCSS(root, darkMode);
        aboutWindow.showAndWait();
    }

    default void openHowToUse(boolean darkMode) {
        Parent root = (Parent) new SpringFXMLLoader().load("/HowToUse.fxml");
        Stage aboutWindow = ViewUtils.getStandardStage("How to use?");
        Scene scene = new Scene(root, 200, 200);
        aboutWindow.setScene(scene);
        updateCSS(root, darkMode);
        aboutWindow.showAndWait();
    }

    default void openRemovePanel(boolean darkMode) {
        Parent root = (Parent) new SpringFXMLLoader().load("/RemoveView.fxml");
        Stage removeWindow = ViewUtils.getStandardStage("Remove");
        Scene scene = new Scene(root, 400, 500);
        removeWindow.setScene(scene);
        updateCSS(root, darkMode);
        removeWindow.showAndWait();
    }
}
