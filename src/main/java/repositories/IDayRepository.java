package repositories;

import model.Day;

@org.springframework.stereotype.Repository
public interface IDayRepository extends Repository {
    Day getDayByDate(String date);
    Day saveDay(Day day);
    void deleteDay(Day day);
}
