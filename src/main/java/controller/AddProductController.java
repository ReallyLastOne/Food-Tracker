package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.IProductRepository;
import view.elements.ProductInformationBox;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddProductController implements Initializable {
    @FXML
    public ProductInformationBox productInformationPanel;
    @FXML
    private Text message;
    @FXML
    private Button addProductButton;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @Autowired
    IProductRepository productRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void addProductButtonClicked() {
        try {
            Product product = productInformationPanel.createProduct();
            productRepository.saveProduct(product);
            displayMessage("Successfully added product!", true);
        } catch (NumberFormatException e) {
            displayMessage("Wrong input for product.", false);
        } catch (IllegalArgumentException e) {
            displayMessage("Name must not be empty.", false);
        } catch (Exception e) {
            displayMessage("Duplicate entry.", false);
        }
    }

    private void displayMessage(String text, boolean success) {
        message.setText(text);
        message.setStyle("-fx-font-size: 18;");
        if(success) {
            message.setFill(Color.LIGHTGREEN);
        } else {
            message.setFill(Color.DARKRED);
        }
    }

    @FXML
    public void okButtonClicked() {
        addProductButtonClicked();
        Stage st = (Stage) okButton.getScene().getWindow();
        st.close();
    }

    @FXML
    public void cancelButtonClicked() {
        Stage st = (Stage) cancelButton.getScene().getWindow();
        st.close();
    }
}
