package view.elements;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import static utils.ModelUtils.*;
/** Class that stores values of current macros in current day.
 * */
public class SummaryBox extends VBox {
    Label caloriesOutputLabel;
    Label carbsOutputLabel;
    Label proteinsOutputLabel;
    Label fatsOutputLabel;

    Label caloriesLabel;
    Label carbsLabel;
    Label proteinsLabel;
    Label fatsLabel;

    private static final int FONT_SIZE = 17;

    public SummaryBox() {
        this.setSpacing(10);
        initializeLabels();

        this.getChildren().addAll(new HBox(caloriesOutputLabel, caloriesLabel));
        this.getChildren().addAll(new HBox(carbsLabel, carbsOutputLabel));
        this.getChildren().addAll(new HBox(proteinsLabel, proteinsOutputLabel));
        this.getChildren().addAll(new HBox(fatsLabel, fatsOutputLabel));
    }

    private void initializeLabels() {
        initializeEditableLabels();
        initializeUneditableLabels();
    }

    private void initializeEditableLabels() {
        caloriesOutputLabel = new Label("0");
        carbsOutputLabel = new Label("0");
        proteinsOutputLabel = new Label("0");
        fatsOutputLabel = new Label("0");

        caloriesOutputLabel.setFont(new Font(FONT_SIZE));
        carbsOutputLabel.setFont(new Font(FONT_SIZE));
        proteinsOutputLabel.setFont(new Font(FONT_SIZE));
        fatsOutputLabel.setFont(new Font(FONT_SIZE));
    }

    private void initializeUneditableLabels() {
        caloriesLabel = new Label(" kcal");
        carbsLabel = new Label("Carbs: ");
        proteinsLabel = new Label("Proteins: ");
        fatsLabel = new Label("Fats: ");

        caloriesLabel.setFont(new Font(FONT_SIZE));
        carbsLabel.setFont(new Font(FONT_SIZE));
        proteinsLabel.setFont(new Font(FONT_SIZE));
        fatsLabel.setFont(new Font(FONT_SIZE));
    }

    private static void addTextToLabel(Label label, String text) {
        label.setText(label.getText() + text);
    }

    public void updateActualDailyMacros(double calories, double carbs, double proteins, double fats) {
        caloriesOutputLabel.setText(String.valueOf((int) calories));
        carbsOutputLabel.setText(String.valueOf((int) carbs));
        proteinsOutputLabel.setText(String.valueOf((int) proteins));
        fatsOutputLabel.setText(String.valueOf((int) fats));
    }

    public void updateDailyMacrosTarget(int dailyCalories, int[] percents) {
        addTextToLabel(caloriesOutputLabel, " / " + dailyCalories);
        addTextToLabel(carbsOutputLabel, " / " + (int) getDailyMacroContribution(percents[0], dailyCalories, CARB_CALORIES_PER_GRAM) + "g");
        addTextToLabel(proteinsOutputLabel, " / " + (int) getDailyMacroContribution(percents[1], dailyCalories, PROTEIN_CALORIES_PER_GRAM) + "g");
        addTextToLabel(fatsOutputLabel, " / " + (int) getDailyMacroContribution(percents[0], dailyCalories, FAT_CALORIES_PER_GRAM) + "g");
    }
}
