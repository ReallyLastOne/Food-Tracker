package repositories;

import model.Product;

import java.util.List;

@org.springframework.stereotype.Repository
public interface IProductRepository extends Repository {
    List<Product> getAllProducts();
    Product getProductByName(String name);
    void deleteProduct(Product product);
    Product saveProduct(Product product);
}