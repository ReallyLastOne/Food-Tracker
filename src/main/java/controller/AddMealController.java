package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.IMealRepository;
import repositories.IProductRepository;
import view.elements.SummaryPane;
import view.elements.MealBox;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddMealController implements Initializable {
    @FXML
    private MealBox mealBox;
    @FXML
    private Text message;
    @FXML
    private Button clear;
    @FXML
    private SummaryPane summaryPane;
    @FXML
    private TextField mealNameInput;

    @FXML
    private Button addMealButton;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @Autowired
    private IMealRepository mealRepository;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MealBox.setSummaryPane(summaryPane);
    }

    @FXML
    private void okButtonClicked(){
        addMealButtonClicked();
        Stage st = (Stage) okButton.getScene().getWindow();
        st.close();
    }

    @FXML
    private void cancelButtonClicked() {
        Stage st = (Stage) cancelButton.getScene().getWindow();
        st.close();
    }

    @FXML
    private void addMealButtonClicked() {
        try {
            Meal meal = mealBox.creteMeal(mealNameInput.getText());
            mealRepository.saveMeal(meal);
            message.setText("Successfully added meal!");
            message.setFill(Color.LIGHTGREEN);
        } catch (Exception e) {
            message.setText(e.getMessage());
            message.setFill(Color.DARKRED);
        }
    }

    @FXML
    private void clearButtonClicked() {
        message.setText("");
        mealBox.resetState();
        summaryPane.resetState();
        mealNameInput.clear();
    }
}
