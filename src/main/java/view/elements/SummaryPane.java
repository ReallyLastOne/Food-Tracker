package view.elements;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.springframework.stereotype.Component;

/** Class that stores values of current macros in inserted meal.
 * */
public class SummaryPane extends GridPane {
    private final Label caloriesLabel = new Label();
    private final Label carbsLabel = new Label();
    private final Label proteinsLabel = new Label();
    private final Label fatsLabel = new Label();
    @Getter
    private double calories, carbs, proteins, fats;

    public SummaryPane() {
        updateMacros(calories, carbs, proteins, fats);
        this.add(caloriesLabel, 1, 0);
        this.add(proteinsLabel, 1, 1);
        this.add(carbsLabel, 3, 0);
        this.add(fatsLabel, 3, 1);
    }

    public void updateMacros(double... macros) {
        updateMacros(macros[0], macros[1], macros[2], macros[3]);
    }

    private void updateMacros(double calories, double carbs, double proteins, double fats) {
        this.caloriesLabel.setText(String.valueOf(calories));
        this.carbsLabel.setText(String.valueOf(carbs));
        this.proteinsLabel.setText(String.valueOf(proteins));
        this.fatsLabel.setText(String.valueOf(fats));
    }

    public void resetState() {
        updateMacros(0.0, 0.0, 0.0, 0.0);
    }
}
