package utils;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;

import static utils.StringUtils.capitalize;

/** Class that represents single day.
 * */
@Getter
public class CalendarDay {
    private final int day;
    private final int month;
    private final int year;

    public CalendarDay(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static CalendarDay getCurrentCalendarDay() {
        LocalDateTime now = LocalDateTime.now();
        return new CalendarDay(now.getDayOfMonth(), now.getMonthValue(), now.getYear());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CalendarDay)) {
            return false;
        }
        CalendarDay calendarDay = (CalendarDay) o;
        return calendarDay.getDay() == this.getDay() && calendarDay.getMonth() == this.getMonth() &&
                calendarDay.getYear() == this.getYear();
    }

    @Override
    public String toString() {
        return day + "." + month + "." + year;
    }

    public String toPrettyDate() {
        return day + " " + capitalize(Month.of(month).toString()) + " " + year;
    }

    public static CalendarDay fromPrettyDateToCalendarDay(String prettyDate) {
        String[] date = prettyDate.split(" ");
        return new CalendarDay(Integer.parseInt(date[0]), Month.valueOf(date[1].toUpperCase()).getValue(), Integer.parseInt(date[2]));
    }
}
