package repositories;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Component
public interface Repository {
    String PERSISTENCE_UNIT_NAME = "myPersistenceUnit";
    EntityManager em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
}
