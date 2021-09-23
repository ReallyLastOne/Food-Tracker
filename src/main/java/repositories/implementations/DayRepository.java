package repositories.implementations;

import model.Day;
import org.springframework.stereotype.Repository;
import repositories.IDayRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@Repository
public class DayRepository implements IDayRepository {

    @Override
    public Day getDayByDate(String date) {
        TypedQuery<Day> q = em.createQuery("SELECT b FROM Day b WHERE b.date = :date", Day.class);
        q.setParameter("date", date);
        return q.getSingleResult();
    }

    @Override
    public Day saveDay(Day day) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            /* no need to save edibles containing in day since they were saved during creation */
            em.persist(day);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return day;
    }

    @Override
    public void deleteDay(Day day) {
        em.remove(em.contains(day) ? day : em.merge(day));
    }
}
