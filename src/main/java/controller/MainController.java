package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Day;
import model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.IDayRepository;
import repositories.ISettingsRepository;
import utils.menu.AddingEdiblesHandler;
import utils.CalendarDay;
import utils.menu.SettingsHandler;
import utils.spring.SpringFXMLLoader;
import view.elements.AppMenu;
import view.elements.CalendarPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import view.elements.DayButton;
import view.elements.SummaryBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utils.ModelUtils.*;
import static utils.ViewUtils.updateCSS;

@Component
public class MainController implements Initializable, AddingEdiblesHandler, SettingsHandler {
    @FXML
    private Label date;
    @FXML
    private ImageView backIcon;
    @FXML
    private ImageView forwardIcon;

    @FXML
    private CalendarPane calendarPane;
    @FXML
    private SummaryBox summaryBox;
    @FXML
    private AppMenu menu;

    @Autowired
    private IDayRepository dayRepository;
    @Autowired
    private ISettingsRepository settingsRepository;
    @Autowired
    private CalendarDay calendarDay;

    private Settings settings;
    private Day day;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveCurrentDayToDatabase();
        initializeSettings();
        updateIncome();
        updateDate();
        setButtonsBehaviour();
    }

    private void saveCurrentDayToDatabase() {
        try {
            day = dayRepository.getDayByDate(calendarDay.toString());
        } catch(Exception ignored) {
            day = dayRepository.saveDay(new Day(calendarDay));
        }
    }

    private void setButtonsBehaviour() {
        for(DayButton button : calendarPane.getDayButtons()) {
            button.setOnAction(e -> {
                try {
                    dayButtonClicked(button.getCalendarDay());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    private void initializeSettings() {
        try {
            settings = settingsRepository.getSettingsById(0);
        } catch (Exception e) {
            settings = Settings.getRegularSettings();
            settingsRepository.saveSettings(settings);
        } finally {
            menu.setDarkMode(settings.isDarkMode());
        }
    }

    private void updateIncome() {
        summaryBox.updateActualDailyMacros(calculateCalories(day), calculateTotalCarbs(day), calculateTotalProteins(day),
                calculateTotalFats(day));
        summaryBox.updateDailyMacrosTarget(settings.getDailyCalories(), settings.getPercents());
    }

    private void updateDate() {
        date.setStyle("-fx-font-weight: bold;");
        date.setText(calendarPane.updateDate());
    }
    
    private void dayButtonClicked(CalendarDay calendarDay) throws IOException {
        menu.setDarkMode(settings.isDarkMode());
        Stage currentStage = (Stage) backIcon.getScene().getWindow();
        Parent o = (Parent) new SpringFXMLLoader().load("/SpecificDayView.fxml");
        currentStage.setScene(new Scene(o, 800, 500));
        updateCSS(o, settings.isDarkMode());
        currentStage.setTitle(calendarDay.toPrettyDate());
    }

    @FXML
    public void back() {
        calendarPane.turnPageUp();
        updateDate();
        setButtonsBehaviour();
    }

    @FXML
    public void forward() {
        calendarPane.turnPageDown();
        updateDate();
        setButtonsBehaviour();
    }

    @FXML
    public void settingsClicked() {
        openSettings(settings.isDarkMode());
        updateCSS(date, settings.isDarkMode());
        menu.setDarkMode(settings.isDarkMode());
    }

    @FXML
    private void addProductClicked() {
        this.openAddProductPanel(settings.isDarkMode());
    }

    @FXML
    private void addMealClicked() {
        this.openAddMealPanel(settings.isDarkMode());
    }
}
