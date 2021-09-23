package repositories.implementations;

import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repositories.IMealRepository;
import repositories.IProductRepository;

import javax.persistence.*;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {
    @Autowired
    IMealRepository mealRepository;

    @Override
    public List<Product> getAllProducts() {
        return em.createQuery("SELECT a FROM Product a WHERE TYPE(a) = Product AND" +
                " a.deprecated = :deprecated", Product.class).setParameter("deprecated", false).getResultList();
    }

    @Override
    public Product getProductByName(String name) {
        Query q = em.createQuery("SELECT o from Product o "
        + "where o.name = :name AND "
        + "o.deprecated = :deprecated");
        q.setParameter("deprecated", false).setParameter("name", name);
        return (Product) q.getSingleResult();
    }

    @Override
    public void deleteProduct(Product product) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(product) ? product : em.merge(product));
        transaction.commit();
    }

    @Override
    public Product saveProduct(Product product) {
        EntityTransaction transaction = em.getTransaction();

        // if no other product/ meal this named, then try to save (probably ugly, but no idea how to fix/ change this.
        try {
            getProductByName(product.getName()); // throws NoResultException
            try {
                mealRepository.getMealByName(product.getName()); // throws NoResultException
            } catch (NoResultException e) {
                throw new IllegalStateException();
            }
            throw new IllegalStateException("Duplicate entry.");
        } catch (Exception f) {
            try {
                if(!(f instanceof NoResultException)) throw f;
                transaction.begin();
                em.persist(product);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                throw new IllegalStateException("Duplicate entry.");
            } catch (Exception e) {
                transaction.rollback();
                throw new IllegalStateException("Unknown error.");
            }
            return product;
        }
    }

    // changes status of a product to deprecated. Doesn't delete the product from day registries.
    // it is provided that product in parameter is in database
    public void transferProductToDeprecated(Product product) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            product = em.find(Product.class, product.getId());
            product.setDeprecated(true);
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
        }
    }
}
