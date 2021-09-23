package repositories;

import model.Meal;

import java.util.List;

@org.springframework.stereotype.Repository
public interface IMealRepository extends Repository {
    List<Meal> getAllMeals();
    Meal getMealByName(String name);
    void deleteMeal(Meal meal);
    Meal saveMeal(Meal meal);
}
