package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.IDayRepository;
import repositories.IMealRepository;
import repositories.IProductRepository;
import repositories.ISettingsRepository;
import utils.CalendarDay;
import utils.spring.SpringFXMLLoader;
import view.elements.AppMenu;
import view.elements.SpecificRegisterTableView;
import view.elements.SummaryPane;
import view.model.EdibleSpecificView;

import javax.persistence.NoResultException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import static utils.ModelUtils.calculateMacros;
import static utils.Constants.APP_NAME;
import static utils.ViewUtils.updateCSS;

@Component
public class SpecificDayController implements Initializable {
    public static final int MAX_PRODUCTS_IN_PANEL = 11;
    @FXML
    private SummaryPane summary;
    @FXML
    SpecificRegisterTableView<EdibleSpecificView> firstPanel;
    @FXML
    SpecificRegisterTableView<EdibleSpecificView> secondPanel;

    @FXML
    private TextField weightInput;
    @FXML
    private TextField nameInput;
    @FXML
    private Label date;
    @FXML
    private ImageView backIcon;
    @FXML
    private AppMenu menu;

    private CalendarDay calendarDay;
    private Day day;

    @Autowired
    private IDayRepository dayRepository;
    @Autowired
    private IMealRepository mealRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ISettingsRepository settingsRepository;

    private Settings settings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            // very ugly, since title is set, we extract calendar day from title
            calendarDay = CalendarDay.fromPrettyDateToCalendarDay(((Stage) backIcon.getScene().getWindow()).getTitle());
            displayDate();
            initializeDay();
            settings = settingsRepository.getSettingsById(0);
            menu.setDarkMode(settings.isDarkMode());
        });
    }

    private void initializeDay() {
        try {
            day = dayRepository.getDayByDate(calendarDay.toString());
            for(Map.Entry<Edible, Double> e : day.getEdibles().entrySet()) {
                addEdibleToPanel(e.getKey(), e.getValue());
            }
        } catch(NoResultException e) {
            day = new Day(calendarDay);
            dayRepository.saveDay(day);
        }
        summary.updateMacros(calculateMacros(day));
    }

    private void displayDate() {
        date.setText(calendarDay.toPrettyDate());
        date.setStyle("-fx-font-weight: bold");
    }

    @FXML
    private void back() {
        Stage currentStage = (Stage) backIcon.getScene().getWindow();
        Parent root = (Parent) new SpringFXMLLoader().load("/MainView.fxml");
        currentStage.setTitle(APP_NAME);
        currentStage.setScene(new Scene(root, 800, 500));
        updateCSS(root, settings.isDarkMode());
    }

    @FXML
    private void add() {
        try {
            Product product = productRepository.getProductByName(nameInput.getText());
            day.addEdible(product, Double.parseDouble(weightInput.getText()));
            dayRepository.saveDay(day);
            addEdibleToPanel(product, Double.parseDouble(weightInput.getText()));
        } catch (Exception e) { // if no product this named in database, search for meal this named
            try {
                Meal meal = mealRepository.getMealByName(nameInput.getText());
                day.addEdible(meal, Double.parseDouble(weightInput.getText()));
                dayRepository.saveDay(day);
                addEdibleToPanel(meal, Double.parseDouble(weightInput.getText()));
            } catch (Exception ignored) { // no edible this named
            }
        }
        summary.updateMacros(calculateMacros(day));
    }

    private void addEdibleToPanel(Edible e, double grams) {
        if(firstPanel.getElements() < MAX_PRODUCTS_IN_PANEL) {
            firstPanel.add(e, grams);
        }
        else if (secondPanel.getElements() < MAX_PRODUCTS_IN_PANEL) {
            secondPanel.add(e, grams);
        }
    }

    @FXML
    private void delete() {
        for(EdibleSpecificView x : firstPanel.getSelectionModel().getSelectedItems()) {
            day.removeEdible(x.getId());
        }
        for(EdibleSpecificView x : secondPanel.getSelectionModel().getSelectedItems()) {
            day.removeEdible(x.getId());
        }
        dayRepository.saveDay(day);
        firstPanel.removeSelected();
        secondPanel.removeSelected();
        summary.updateMacros(calculateMacros(day));
    }
}