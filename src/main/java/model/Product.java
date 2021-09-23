package model;

import lombok.*;

import javax.persistence.*;
import static utils.ModelUtils.calculateCalories;

/** Class that represents single product, basic indivisible unit.
 * */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends Edible {
    @Column(name = "total_grams", nullable = false)
    final double totalGrams = 100;

    public Product(String name, double carbs, double proteins, double fats) {
        if(name == null || name.equals("")) throw new IllegalArgumentException("Name of product not entered.");
        if(carbs < 0 || proteins < 0 || fats < 0) throw new IllegalArgumentException("Product can't have negative amount of any macronutrient.");
        this.name = name;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
        this.calories = calculateCalories(carbs, proteins, fats);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if(!(o instanceof Product)) return false;
        Product product = (Product) o;
        return name.equals(product.getName()) && carbs == product.getCarbs() && proteins == product.getProteins()
                && fats == product.getFats();
    }

    @Override
    public String toString() {
        return "Product{name=" + name + ", id=" + id + ", carbs=" + carbs + ", proteins=" + proteins + ", fats=" + fats
                + ", totalGrams=" + totalGrams + "}";
    }
}
