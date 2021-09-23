package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Edible;
import model.Meal;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.implementations.MealRepository;
import repositories.implementations.ProductRepository;
import view.elements.OverviewRegisterTableView;
import view.model.EdibleOverview;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class RemoveController implements Initializable {
    @FXML
    OverviewRegisterTableView<EdibleOverview> panel;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    MealRepository mealRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAllEdibles();
    }

    private void initializeAllEdibles() {
        List<Product> products = productRepository.getAllProducts();
        List<Meal> meals = mealRepository.getAllMeals();
        products.forEach(e -> panel.add(e));
        meals.forEach(e -> panel.add(e));
    }

    @FXML
    private void deleteClicked() {
        ObservableList<EdibleOverview> list = panel.delete();
        for(EdibleOverview x : list) {
            if(x.getMeal() == null) {
                productRepository.transferProductToDeprecated(x.getProduct());
            } else if (x.getProduct() == null) {
                mealRepository.transferMealToDeprecated(x.getMeal());
            }
        }
    }
}
