package utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

import static utils.Constants.DAYS_DISPLAYED_IN_MONTH;
import static utils.StringUtils.capitalize;

/** Class that represents a structure of days in a specific calendar month.
 * */
@Component
public class CalendarPage {
    private final Map<String, Integer> dayValues = Map.of("MONDAY", 0, "TUESDAY", 1, "WEDNESDAY", 2,
            "THURSDAY", 3, "FRIDAY", 4, "SATURDAY", 5, "SUNDAY", 6);

    private final int[] dayNumbers = new int[DAYS_DISPLAYED_IN_MONTH];
    private final CalendarDay[] days = new CalendarDay[DAYS_DISPLAYED_IN_MONTH];
    private final String firstDayOfAMonth;
    private int currentMonth;
    private int currentYear;
    private final LocalDateTime date;

    /** Creating CalendarPage that represents given month and year.
     * */
    public CalendarPage(int month, int year) {
        if(month <= 0) throw new IllegalArgumentException();
        this.currentMonth = month;
        this.currentYear = year;
        date = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime l2 = date.with(TemporalAdjusters.firstDayOfMonth());
        firstDayOfAMonth = l2.getDayOfWeek().toString();
        calculateDayNumbers();
    }

    public CalendarPage() {
        this(LocalDateTime.now().getMonth().getValue(), LocalDateTime.now().getYear());
    }

    public CalendarPage previous() {
        if(currentMonth == 1) {
            currentMonth = 12;
            currentYear -= 1;
        }
        else {
            currentMonth -= 1;
        }
        return new CalendarPage(currentMonth, currentYear);
    }

    public CalendarPage next() {
        if(currentMonth == 12) {
            currentMonth = 1;
            currentYear += 1;
        }
        else {
            currentMonth += 1;
        }
        return new CalendarPage(currentMonth, currentYear);
    }

    public String getDate() {
        return capitalize(date.getMonth().toString() + " " + date.getYear());
    }

    private void calculateDayNumbers() {
        LocalDateTime previousMonthDate = date.minus(Period.ofMonths(1));

        YearMonth previousYearMonth = YearMonth.from(previousMonthDate);
        YearMonth currentYearMonth = YearMonth.from(date);

        int daysInPreviousMonth = previousYearMonth.lengthOfMonth();
        int daysInCurrentMonth = currentYearMonth.lengthOfMonth();

        days[dayValues.get(firstDayOfAMonth)] = new CalendarDay(dayValues.get(firstDayOfAMonth), currentMonth,
                currentYear);
        dayNumbers[dayValues.get(firstDayOfAMonth)] = 1;

        fillPreviousMonth(daysInPreviousMonth);
        fillCurrentMonth(daysInCurrentMonth);
        fillNextMonth(daysInCurrentMonth);
    }

    private int calculateNextMonth(int currentMonth) {
        if(currentMonth == 12) return 1;
        return currentMonth + 1;
    }

    private int calculateYear(int currentMonth, int currentYear) {
        if(currentMonth == 1) return currentYear - 1;
        if(currentMonth == 12) return currentYear + 1;
        return currentYear;
    }

    private void fillPreviousMonth(int daysInPreviousMonth) {
        int currentDay = 0;
        for(int i = dayValues.get(firstDayOfAMonth) - 1; i >= 0; i--) {
            days[i] = new CalendarDay(daysInPreviousMonth - currentDay,
                    calculatePreviousMonth(currentMonth), calculateYear(currentMonth,
                    currentYear));
            dayNumbers[i] = daysInPreviousMonth - currentDay;
            currentDay++;
        }
    }

    private void fillCurrentMonth(int daysInCurrentMonth) {
        int currentDay = 1;
        for(int i = dayValues.get(firstDayOfAMonth); i < daysInCurrentMonth + dayValues.get(firstDayOfAMonth); i++) {
            days[i] = new CalendarDay(currentDay, currentMonth, currentYear);
            dayNumbers[i] = currentDay;
            currentDay++;
        }
    }

    private void fillNextMonth(int daysInCurrentMonth) {
        int currentDay = 1;
        for(int i = daysInCurrentMonth + dayValues.get(firstDayOfAMonth); i < DAYS_DISPLAYED_IN_MONTH; i++) {
            days[i] = new CalendarDay(currentDay, calculateNextMonth(currentMonth), calculateYear(currentMonth, currentYear));
            dayNumbers[i] = currentDay;
            currentDay++;
        }
    }

    private int calculatePreviousMonth(int currentMonth) {
        if(currentMonth == 1) return 12;
        return currentMonth - 1;
    }

    public int[] getDayNumbers() {
        return dayNumbers;
    }

    @Override
    public String toString() {
        return currentMonth + " " + currentYear;
    }

    public int getCurrentYear() {
        return this.currentYear;
    }

    public int getCurrentMonth() {
        return this.currentMonth;
    }

    public CalendarDay[] getCalendarDays() {
        return this.days;
    }
}
