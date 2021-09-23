import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import repositories.ISettingsRepository;
import repositories.implementations.SettingsRepository;
import utils.spring.SpringFXMLLoader;

import static utils.Constants.APP_NAME;
import static utils.ViewUtils.updateCSS;

public class FoodTracker extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = (Parent) new SpringFXMLLoader().load("/MainView.fxml");
        primaryStage.getIcons().add(new Image("/applePNG.png"));
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setTitle(APP_NAME);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        //ugly
        ISettingsRepository settingsRepository = new SettingsRepository();
        updateCSS(root, settingsRepository.getSettingsById(0).isDarkMode());
        primaryStage.show();
    }
}