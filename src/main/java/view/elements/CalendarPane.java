package view.elements;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.springframework.stereotype.Component;
import utils.CalendarDay;
import utils.CalendarPage;

import static utils.Constants.*;

/** Class that is a view representation of calendar page.
 * */
@Component
public class CalendarPane extends GridPane {
    private CalendarPage currentCalendarPage;
    @Getter
    private DayButton[] dayButtons = new DayButton[DAYS_DISPLAYED_IN_MONTH];

    public CalendarPane() {
        super();
        currentCalendarPage = new CalendarPage();

        initializeDayNameLabels();
        updateView();
    }

    public void turnPageDown() {
        currentCalendarPage = currentCalendarPage.previous();
        updateDate();
        clearDaysPanel();
        updateView();
    }

    public void turnPageUp() {
        currentCalendarPage = currentCalendarPage.next();
        updateDate();
        clearDaysPanel();
        updateView();
    }

    public String updateDate() {
        return currentCalendarPage.getDate();
    }

    private void updateView() {
        int dayCount = 0;
        initializeDayNameLabels();
        CalendarDay[] days = currentCalendarPage.getCalendarDays();
        for(int i = 1; i < CALENDAR_ROWS + 1; i++) {
            for(int j = 0; j < CALENDAR_DAYS; j++) {
                DayButton btn = new DayButton(days[dayCount]);
                dayButtons[dayCount] = btn;
                this.add(btn, j, i);
                dayCount++;
            }
        }
    }

    private void clearDaysPanel() {
        this.getChildren().clear();
    }

    private void initializeDayNameLabels() {
        for(int i = 0; i < CALENDAR_DAYS; i++) {
            Label label = new Label(DAYS_NAME[i]);
            this.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }
}
