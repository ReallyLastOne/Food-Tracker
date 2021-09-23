package repositories.implementations;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repositories.IMealRepository;
import repositories.IProductRepository;

import javax.persistence.*;
import java.util.List;

@Repository
public class MealRepository implements IMealRepository {
    @Autowired
    IProductRepository productRepository;

    @Override
    public List<Meal> getAllMeals() {
        return em.createQuery("SELECT a FROM Meal a WHERE TYPE(a) = Meal AND a.deprecated = false", Meal.class).getResultList();
    }

    @Override
    public Meal getMealByName(String name) {
        Query q = em.createQuery("SELECT o from Meal o "
                + "where o.name = :name AND "
                + "o.deprecated = :deprecated");
        q.setParameter("deprecated", false).setParameter("name", name);
        return (Meal) q.getSingleResult();
    }

    @Override
    public void deleteMeal(Meal meal) {
        em.remove(em.contains(meal) ? meal : em.merge(meal));
    }

    @Override
    public Meal saveMeal(Meal meal) {
        EntityTransaction transaction = em.getTransaction();

        try {
            getMealByName(meal.getName()); // throws NoResultException
            try {
                productRepository.getProductByName(meal.getName()); // throws NoResultException
            } catch (Exception e) {
                throw new IllegalStateException();
            }
            throw new IllegalStateException("Duplicate entry.");
        } catch(Exception f) {
            try {
                if (!(f instanceof NoResultException)) throw f;
                transaction.begin();
                for (Product x : meal.getProducts().keySet()) {
                    em.persist(em.contains(x) ? x : em.merge(x));
                    //em.persist(x);
                }
                em.persist(meal);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                throw new IllegalStateException("Duplicate entry.");
            } catch (Exception e) {
                transaction.rollback();
                throw new IllegalStateException("Unknown error.");
            }
        }
        return meal;
    }

    public void transferMealToDeprecated(Meal meal) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            meal = em.find(Meal.class, meal.getId());
            meal.setDeprecated(true);
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
        }
    }
}
