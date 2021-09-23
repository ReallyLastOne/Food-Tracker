package repositories.implementations;

import model.Meal;
import model.Settings;
import org.springframework.stereotype.Repository;
import repositories.ISettingsRepository;

import javax.persistence.*;

@Repository
public class SettingsRepository implements ISettingsRepository {

    @Override
    public Settings getSettingsById(int id) {
        TypedQuery<Settings> q = em.createQuery("SELECT b FROM Settings b WHERE b.id = :id", Settings.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    @Override
    public Settings saveSettings(Settings settings) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(settings);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return settings;
    }

    @Override
    public void deleteSettings(Settings settings) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(settings) ? settings : em.merge(settings));
        transaction.commit();
    }
}
