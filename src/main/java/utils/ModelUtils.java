package utils;

import model.Day;
import model.Edible;
import model.Meal;
import model.Product;

import java.util.Map;

public class ModelUtils {
    public static final int CARB_CALORIES_PER_GRAM = 4;
    public static final int PROTEIN_CALORIES_PER_GRAM = 4;
    public static final int FAT_CALORIES_PER_GRAM = 9;

    public static double calculateCalories(double carbs, double proteins, double fats) {
        return CARB_CALORIES_PER_GRAM * carbs + PROTEIN_CALORIES_PER_GRAM * proteins + FAT_CALORIES_PER_GRAM * fats;
    }

    public static double calculateCalories(Product product) {
        return calculateCalories(product.getCarbs(), product.getProteins(), product.getFats());
    }

    public static double calculateCalories(Meal meal) {
        double totalCals = 0d;
        for(Product product : meal.getProducts().keySet()) totalCals += calculateCalories(product) * meal.getProducts().get(product)/100;
        return totalCals;
    }

    public static double calculateCalories(Edible edible) {
        if(edible instanceof Product) return calculateCalories((Product) edible);
        return calculateCalories((Meal) edible);
    }

    public static double calculateCalories(Map<Edible, Double> edibles) {
        double sum = 0d;
        for(Map.Entry<Edible, Double> x : edibles.entrySet()) {
            sum += x.getKey().getCalories() * x.getValue()/x.getKey().getTotalGrams()  ;
        }
        return sum;
    }

    public static double calculateCalories(Day day) {
        return calculateCalories(day.getEdibles());
    }

    public static double calculateTotalCarbs(Day day) {
        double sum = 0d;
        for(Map.Entry<Edible, Double> x : day.getEdibles().entrySet()) {
            sum += x.getKey().getCarbs() * x.getValue()/x.getKey().getTotalGrams();
        }
        return sum;
    }

    public static double calculateTotalProteins(Day day) {
        double sum = 0d;
        for(Map.Entry<Edible, Double> x : day.getEdibles().entrySet()) {
            sum += x.getKey().getProteins() * x.getValue()/x.getKey().getTotalGrams();
        }
        return sum;
    }

    public static double calculateTotalFats(Day day) {
        double sum = 0d;
        for(Map.Entry<Edible, Double> x : day.getEdibles().entrySet()) {
            sum += x.getKey().getFats() * x.getValue()/x.getKey().getTotalGrams();
        }
        return sum;
    }

    public static double[] calculateMacros(Day day) {
        return new double[] {calculateCalories(day), calculateTotalCarbs(day), calculateTotalProteins(day),
                calculateTotalFats(day)};
    }

    public static double getDailyMacroContribution(int percent, int dailyCalories, int macroCalories) {
        return (double) percent/100 * dailyCalories/ macroCalories;
    }

}
