package view.elements;

import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import model.Meal;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.implementations.ProductRepository;

import static utils.Constants.MAX_PRODUCTS_IN_MEAL;

/** Responsible for input panel in meal adding menu and handling input. Dynamically calculates actual macro distribution
 * for products entered.
 * */
@Getter
public class MealBox extends VBox {
    @Autowired
    private final ProductRepository productRepository = new ProductRepository();
    private final MealRow[] rows = new MealRow[MAX_PRODUCTS_IN_MEAL];
    private final MealManager mealManager = new MealManager();
    @Setter
    private static SummaryPane summaryPane;

    public MealBox() {
        initializeRows();
    }

    private void initializeRows() {
        MealRow.setMealBox(this);
        for(int i = 0; i < MAX_PRODUCTS_IN_MEAL; i++) {
            rows[i] = new MealRow(i);
            this.getChildren().add(rows[i]);
        }
    }

    void rowChanged(int id)  {
        try {
            Product product = productRepository.getProductByName(rows[id].getNameInput().getText());
            double weight = Double.parseDouble(rows[id].getWeightInput().getText());
            mealManager.addProduct(product, weight, id);
        } catch (Exception ignored) {
            mealManager.deleteProduct(id);
        } finally {
            summaryPane.updateMacros(mealManager.fullMacros);
        }
    }

    public Meal creteMeal(String name) {
        return mealManager.createMeal(name);
    }

    public void resetState() {
        for(MealRow row : rows) {
            row.clearTextFields();
        }
        mealManager.reset();
    }

    /** Responsible for handling current state of product input in meal adding panel.
     * */
    static class MealManager {
        private Product[] products = new Product[MAX_PRODUCTS_IN_MEAL];
        private double[] productsWeight = new double[MAX_PRODUCTS_IN_MEAL];
        private double[][] productMacros = new double[MAX_PRODUCTS_IN_MEAL][4];
        private double[] fullMacros = new double[4];

        void deleteProduct(int id) {
            productMacros[id] = new double[4];
            products[id] = null;
            productsWeight[id] = 0.0;
            updateFullMacros();
        }

        void addProduct(Product product, double grams, int id) {
            if(getCountOfProduct(product, products) < 1 || products[id].getName().equals(product.getName())) {
                products[id] = product;
                productsWeight[id] = grams;
                updateProductMacros(id);
                updateFullMacros();
            }
        }

        Meal createMeal(String name) {
            Meal meal = new Meal(name);
            for(int i = 0; i < MAX_PRODUCTS_IN_MEAL; i++) {
                if(products[i] != null) meal.addProduct(products[i], productsWeight[i]);
            }
            if(meal.getProducts().size() == 0) throw new IllegalStateException("Not enough products in meal.");
            return meal;
        }

        private static int getCountOfProduct(Product product, Product[] products) {
            int count = 0;
            for (Product x : products) {
                if(x != null) {
                    if (x.getName().equals(product.getName())) count += 1;
                }
            }
            return count;
        }

        void reset() {
            products = new Product[MAX_PRODUCTS_IN_MEAL];
            productsWeight = new double[MAX_PRODUCTS_IN_MEAL];
            productMacros = new double[MAX_PRODUCTS_IN_MEAL][4];
            fullMacros = new double[4];
        }

        private void updateProductMacros(int id) {
            productMacros[id][0] = products[id].getCalories() * productsWeight[id] / 100;
            productMacros[id][1] = products[id].getCarbs() * productsWeight[id] / 100;
            productMacros[id][2] = products[id].getProteins() * productsWeight[id] / 100;
            productMacros[id][3] = products[id].getFats() * productsWeight[id] / 100;
        }

        private void updateFullMacros() {
            fullMacros = new double[4];
            for (double[] row : productMacros) {
                fullMacros[0] += row[0];
                fullMacros[1] += row[1];
                fullMacros[2] += row[2];
                fullMacros[3] += row[3];
            }
        }
    }
}