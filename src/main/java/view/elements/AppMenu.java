package view.elements;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import lombok.Setter;
import org.springframework.stereotype.Component;
import utils.menu.MenuHandler;

@Component
/** Class that is a view representation of menu. */
public class AppMenu extends MenuBar implements MenuHandler {
    @Setter
    private boolean darkMode = false;

    private final Menu generalMenu;
    private final Menu helpMenu;

    public AppMenu() {
        super();
        generalMenu = new Menu("General");
        helpMenu = new Menu("Help");
        this.getMenus().addAll(generalMenu, helpMenu);

        initializeGeneralMenu();
        initializeHelpMenu();
    }

    private void initializeHelpMenu() {
        MenuItem about = new MenuItem("About");
        about.setOnAction(e -> this.openAbout(darkMode));
        helpMenu.getItems().add(about);

        MenuItem help = new MenuItem("How to use?");
        help.setOnAction(e -> this.openHowToUse(darkMode));
        helpMenu.getItems().add(help);
    }

    private void initializeGeneralMenu() {
        MenuItem addProduct = new MenuItem("Add product");
        addProduct.setOnAction(e -> this.openAddProductPanel(darkMode));
        generalMenu.getItems().add(addProduct);


        MenuItem addMeal = new MenuItem("Add meal");
        addMeal.setOnAction(e -> this.openAddMealPanel(darkMode));
        generalMenu.getItems().add(addMeal);


        MenuItem remove = new MenuItem("Remove");
        remove.setOnAction(e -> this.openRemovePanel(darkMode));
        generalMenu.getItems().add(remove);
    }
}
