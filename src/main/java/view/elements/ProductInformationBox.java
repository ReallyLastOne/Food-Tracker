package view.elements;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Product;
import org.springframework.stereotype.Component;
import repositories.IProductRepository;

/** Class that is responsible for storing input entered by user. Handles correct product creation.
 * */
@Component
public class ProductInformationBox extends VBox {
    private TextField nameInput;
    private TextField carbsInput;
    private TextField proteinsInput;
    private TextField fatsInput;

    public ProductInformationBox() {
        super();
        this.setSpacing(10);
        nameInput = new TextField();
        carbsInput = new TextField();
        proteinsInput = new TextField();
        fatsInput = new TextField();
        setTextFieldsWidth();

        addRow(new Label("Name: "), nameInput);
        addRow(new Label("Carbs: "), carbsInput);
        addRow(new Label("Proteins: "), proteinsInput);
        addRow(new Label("Fats: "), fatsInput);
    }

    public Product createProduct() {
        String name = nameInput.getText();
        if (name.equals("")) throw new IllegalArgumentException();
        double carbs = Double.parseDouble(carbsInput.getText());
        double proteins = Double.parseDouble(proteinsInput.getText());
        double fats = Double.parseDouble(fatsInput.getText());
        return new Product(name, carbs, proteins, fats);
    }

    private void addRow(Label label, TextField textField) {
        GridPane grid = new GridPane();
        label.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
        grid.add(label, 0, 0);
        grid.add(textField, 1, 0);

        grid.getColumnConstraints().addAll(new ColumnConstraints(75), new ColumnConstraints(75));
        this.getChildren().add(grid);
    }

    private void setTextFieldsWidth() {
        nameInput.setPrefWidth(70);
        carbsInput.setPrefWidth(70);
        proteinsInput.setPrefWidth(70);
        fatsInput.setPrefWidth(70);
    }
}
