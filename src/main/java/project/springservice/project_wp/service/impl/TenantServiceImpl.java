package project.springservice.project_wp.service.impl;

import org.springframework.stereotype.Service;
import project.springservice.project_wp.model.*;
import project.springservice.project_wp.model.exceptions.CategoryNotFoundException;
import project.springservice.project_wp.model.exceptions.ProductNotFoundException;
import project.springservice.project_wp.model.exceptions.TenantNotFoundException;
import project.springservice.project_wp.model.exceptions.UserNotFoundException;
import project.springservice.project_wp.repository.CategoryRepository;
import project.springservice.project_wp.repository.ProductRepository;
import project.springservice.project_wp.repository.TenantRepository;
import project.springservice.project_wp.repository.UserRepository;
import project.springservice.project_wp.service.TenantService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;

    public TenantServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, TenantRepository tenantRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Tenant> GetAllTenants() {
        return this.tenantRepository.findAll();
    }

    @Override
    public Tenant GetTenant(Long id) {
        return this.tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException(id));
    }

    //    private String name;
//    @ManyToOne
//    private User owner;
//    private String description;
//    private String logoUrl;
//    private String color;
//    private String address;
//    private String phoneNumber;
//    private String email;
//    private double rating;
//    private LocalDateTime startingTime;
//    private LocalDateTime endingTime;
//    private String facebookLink;
//    private String instagramLink;
//
//    @ManyToOne
//    private Category category;
//
//    @ManyToMany
//    private List<Schedule> schedules;
//    @ManyToMany
//    private List<Product> productsInTenant;
//
//    public int appointmentTime;
    @Override
    public Optional<Tenant> CreateNewTenant(String name, String ownerUsername, String description, String logoUrl,
                                  String color, String address, String phoneNumber, String email, double rating,
                                  LocalDateTime startingTime, LocalDateTime endingTime, String facebookLink, String instagramLink,
                                  Long categoryId, List<Long> productIds, int appointmentTime) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        User user = this.userRepository.findById(ownerUsername)
                .orElseThrow(() -> new UserNotFoundException(ownerUsername));

        List<Product> products = this.productRepository.findAllById(productIds);

        Tenant tenant = new Tenant(name,user,description, logoUrl, color, address, phoneNumber, email, rating, startingTime, endingTime,facebookLink, instagramLink,category, products, appointmentTime);
        tenantRepository.save(tenant);
        return Optional.of(tenant);
    }

    @Override
    public void DeleteTenant(Long id) {
        Tenant tenant = this.GetTenant(id);
        this.tenantRepository.delete(tenant);
    }

    @Override
    public Optional<Tenant> UpdeteExistingTenant(Long tenantId,
                                     String name, String ownerUsername, String description, String logoUrl,
                                     String color, String address, String phoneNumber, String email, double rating,
                                     LocalDateTime startingTime, LocalDateTime endingTime, String facebookLink, String instagramLink,
                                     Long categoryId, List<Long> productIds, int appointmentTime) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        User user = this.userRepository.findById(ownerUsername)
                .orElseThrow(() -> new UserNotFoundException(ownerUsername));
        Tenant tenant = this.tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException(tenantId));

        List<Product> products = this.productRepository.findAllById(productIds);


        tenant.setName(name);
        tenant.setOwner(user);
        tenant.setDescription(description);
        tenant.setLogoUrl(logoUrl);
        tenant.setColor(color);
        tenant.setAddress(address);
        tenant.setPhoneNumber(phoneNumber);
        tenant.setEmail(email);
        tenant.setRating(rating);
        tenant.setStartingTime(startingTime);
        tenant.setEndingTime(endingTime);
        tenant.setFacebookLink(facebookLink);
        tenant.setInstagramLink(instagramLink);
        tenant.setCategory(category);
        tenant.setProductsInTenant(products);
        tenant.setAppointmentTime(appointmentTime);

        tenantRepository.save(tenant);
        return Optional.of(tenant);

    }

    @Override
    public List<Category> GetAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public List<Product> GetAllProductsForTenantCategory(Long categoryId, Long tenantId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Tenant tenant = this.GetTenant(tenantId);
        //
        return null;
    }

    @Override
    public boolean AddProductToTenant(Long tenantId, Long productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Tenant tenant = this.GetTenant(tenantId);
        tenant.getProductsInTenant().add(product);
        this.tenantRepository.save(tenant);
        return true;
    }

    @Override
    public boolean Schedule(Schedule schedule) {
        return false;
    }

    @Override
    public List<Schedule> GetAllSchedulesForDate(Long tenantId, LocalDateTime date) {
        return null;
    }
}
