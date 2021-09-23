package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/** Class that represents single meal which consists of multiple products.
 * */
@Getter
@NoArgsConstructor
@Entity
public class Meal extends Edible {
    @Column(name = "total_grams", nullable = false)
    double totalGrams = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="meal_product")
    @MapKeyJoinColumn(name="product_id")
    @Column(name="grams")
    Map<Product, Double> products = new HashMap<>();

    public Meal(String name) {
        if(name == null || name.equals("")) throw new IllegalArgumentException("Name of meal not entered.");
        this.name = name;
    }

    public void addProduct(Product product, double grams) {
        totalGrams += grams;
        this.products.put(product, grams);
        updateMacros(product,  grams/100);
    }

    private void updateMacros(Product product, double weight) {
        this.calories += weight * product.getCalories();
        this.carbs += weight * product.getCarbs();
        this.proteins += weight * product.getProteins();
        this.fats += weight * product.getFats();
    }

    @Override
    public String toString() {
        return "Meal{name=" + name + ", id=" + id + ", carbs=" + carbs + ", proteins=" + proteins + ", fats=" + fats
                + ", products=" + products + ", totalGrams=" + totalGrams + "}";
    }
}
