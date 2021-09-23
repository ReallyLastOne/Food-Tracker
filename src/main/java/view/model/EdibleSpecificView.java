package view.model;

import lombok.Getter;
import lombok.Setter;
import model.Edible;

/** Class that is a base for single entry in SpecificDayView.
 * */
@Getter
@Setter
public class EdibleSpecificView {
    String name;
    double calories;
    double carbs;
    double proteins;
    double fats;
    double totalGrams;
    int id;

    public EdibleSpecificView(Edible e, double grams) {
        this.id = e.getId();
        this.name = e.getName();
        this.calories = e.getCalories() * grams/e.getTotalGrams();
        this.carbs = e.getCarbs() * grams/e.getTotalGrams();
        this.proteins = e.getProteins() * grams/e.getTotalGrams();
        this.fats = e.getFats() * grams/e.getTotalGrams();
        this.totalGrams = grams;
    }
}
