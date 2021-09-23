package view.elements;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

/** Class that represents single row in meal adding panel.
 * */
@Getter
public class MealRow extends HBox {
    private final int ID; // 'id' gives an error
    private final TextField nameInput;
    private final TextField weightInput;
    @Setter
    private static MealBox mealBox;

    public MealRow(int ID) {
        this.ID = ID;
        this.nameInput = new TextField();
        this.weightInput = new TextField();
        this.getChildren().addAll(nameInput, weightInput);
        initializeListeners();
    }

    private void initializeListeners() {
        nameInput.textProperty().addListener((observable, oldValue, newValue) -> mealBox.rowChanged(ID));
        weightInput.textProperty().addListener((observable, oldValue, newValue) -> mealBox.rowChanged(ID));
    }

    public void clearTextFields() {
        nameInput.clear();
        weightInput.clear();
    }
}
