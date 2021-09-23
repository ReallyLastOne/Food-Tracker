package view.elements;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import lombok.Getter;
import utils.CalendarDay;

import static utils.Constants.DAY_BUTTON_FONT_SIZE;
import static utils.Constants.DAY_BUTTON_SIDE;

@Getter
public final class DayButton extends Button {
    private CalendarDay calendarDay;

    /*@Deprecated
    public DayButton(int text) {
        super();
        this.setText(String.valueOf(text));
        this.setPrefSize(DAY_BUTTON_SIDE, DAY_BUTTON_SIDE);
        this.setFont(new Font(DAY_BUTTON_FONT_SIZE));
    }*/

    public DayButton(CalendarDay calendarDay) {
        super();
        this.calendarDay = calendarDay;
        this.setPrefSize(DAY_BUTTON_SIDE, DAY_BUTTON_SIDE);
        if(calendarDay.equals(CalendarDay.getCurrentCalendarDay())) {
            this.setStyle("-fx-background-color: MediumSeaGreen");
        }
        this.setText(String.valueOf(calendarDay.getDay()));
        this.setFont(new Font(DAY_BUTTON_FONT_SIZE));

    }
}
