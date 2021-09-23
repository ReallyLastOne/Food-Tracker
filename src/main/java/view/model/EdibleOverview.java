package view.model;

import lombok.Getter;
import lombok.Setter;
import model.Edible;
import model.Meal;
import model.Product;

/** Class that is a base in to display in RemoveView.
 * */
@Getter
@Setter
public class EdibleOverview  {
    private Product product;
    private Meal meal;

    private String type;
    String name;
    double calories;
    double carbs;
    double proteins;
    double fats;
    double totalGrams;

    public EdibleOverview(Edible e) {
        this.name = e.getName();
        this.calories = e.getCalories();
        this.carbs = e.getCarbs();
        this.proteins = e.getProteins();
        this.fats = e.getFats();
        this.totalGrams = e.getTotalGrams();

        if(e instanceof Product) {
            product = (Product) e;
            type = "Product";
        }

        else if(e instanceof Meal) {
            meal = (Meal) e;
            type = "Meal";
        }

    }
}
