package utils;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {
    public static final String darkModeCSS = "darkMode.css";

    public static void updateCSS(Node node, boolean darkMode) {
        if(darkMode) {
            node.getScene().getStylesheets().add(darkModeCSS);
        } else {
            node.getScene().getStylesheets().removeAll(darkModeCSS);
        }
    }

    public static Stage getStandardStage(String name) {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/applePNG.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(name);
        stage.setResizable(false);
        return stage;
    }
}
