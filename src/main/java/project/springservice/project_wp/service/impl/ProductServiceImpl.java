package project.springservice.project_wp.service.impl;

import org.springframework.stereotype.Service;
import project.springservice.project_wp.model.*;
import project.springservice.project_wp.model.exceptions.CategoryNotFoundException;
import project.springservice.project_wp.model.exceptions.ProductNotFoundException;
import project.springservice.project_wp.model.exceptions.TenantNotFoundException;
import project.springservice.project_wp.repository.CategoryRepository;
import project.springservice.project_wp.repository.ProductRepository;
import project.springservice.project_wp.repository.TenantRepository;
import project.springservice.project_wp.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TenantRepository tenantRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, TenantRepository tenantRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public List<Product> GetAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product GetProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    //TODO: implement add PRODUCT to TENANT

    @Override
    public Optional<Product> CreateNewProduct(String name, double price, CategoryEnum categoryEnum, String imageUrl, ProductEnum productEnum) {
        Category category = this.categoryRepository.findByCategoryEnum(categoryEnum)
                .orElseThrow(() -> new CategoryNotFoundException(categoryEnum.name()));

        Product product = new Product(name, price, category, imageUrl, productEnum);
        productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public void DeleteProduct(Long id) {
        Product product = this.GetProduct(id);
        this.productRepository.delete(product);
    }

    @Override
    public List<ProductEnum> getAllProductEnums() {
        return Stream.of(ProductEnum.values()).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> UpdeteExistingProduct(Long Id, String name, double price, CategoryEnum categoryEnum, String imageUrl, ProductEnum productEnum) {

        Category category = this.categoryRepository.findByCategoryEnum(categoryEnum)
                .orElseThrow(() -> new CategoryNotFoundException(categoryEnum.name()));



        Product product = this.GetProduct(Id);
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setImageUrl(imageUrl);
        product.setProductEnum(productEnum);

        productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public List<Product> filterByCategory(String searchByCategory) {
        String finalSearchByCategory = searchByCategory.toLowerCase();
        return this.productRepository.findAll()
                .stream()
                .filter(each -> each.getCategory().getName().toLowerCase().equalsIgnoreCase(finalSearchByCategory)
                || each.getCategory().getName().toLowerCase().startsWith(finalSearchByCategory) ||
                        each.getCategory().getName().toLowerCase().endsWith(finalSearchByCategory)
                || each.getCategory().getName().toLowerCase().contains(finalSearchByCategory))
                .collect(Collectors.toList());
    }
}
