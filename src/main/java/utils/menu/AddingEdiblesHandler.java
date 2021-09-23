package utils.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.ViewUtils;
import utils.spring.SpringFXMLLoader;

import static utils.ViewUtils.updateCSS;

public interface AddingEdiblesHandler {
    default void openAddProductPanel(boolean darkMode) {
        Parent root = (Parent) new SpringFXMLLoader().load("/AddProductView.fxml");
        Stage addMealWindow = ViewUtils.getStandardStage("Add product to database");
        Scene scene = new Scene(root, 300, 400);
        addMealWindow.setScene(scene);
        updateCSS(root, darkMode);
        addMealWindow.showAndWait();
    }

    default void openAddMealPanel(boolean darkMode) {
        Parent root = (Parent) new SpringFXMLLoader().load("/AddMealView.fxml");
        Stage addMealWindow = ViewUtils.getStandardStage("Add meal to database");
        Scene scene = new Scene(root, 300, 460);
        addMealWindow.setScene(scene);
        updateCSS(root, darkMode);
        addMealWindow.showAndWait();
    }
}
