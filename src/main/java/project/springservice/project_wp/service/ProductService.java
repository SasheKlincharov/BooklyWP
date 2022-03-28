package project.springservice.project_wp.service;

import project.springservice.project_wp.model.CategoryEnum;
import project.springservice.project_wp.model.Product;
import project.springservice.project_wp.model.ProductEnum;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> GetAllProducts();

    Product GetProduct(Long id);

    Optional<Product> CreateNewProduct(String name, double price, CategoryEnum categoryEnum, String imageUrl, ProductEnum productEnum);

    void DeleteProduct(Long id);

    List<ProductEnum> getAllProductEnums();

    Optional<Product> UpdeteExistingProduct(Long Id, String name, double price, CategoryEnum categoryEnum, String imageUrl, ProductEnum productEnum);
}
