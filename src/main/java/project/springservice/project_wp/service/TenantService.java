package project.springservice.project_wp.service;

import project.springservice.project_wp.model.Category;
import project.springservice.project_wp.model.Product;
import project.springservice.project_wp.model.Schedule;
import project.springservice.project_wp.model.Tenant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TenantService {
    List<Tenant> GetAllTenants();

    Tenant GetTenant(Long id);

    Optional<Tenant> CreateNewTenant(String name, String ownerUsername, String description, String logoUrl,
                                     String color, String address, String phoneNumber, String email, double rating,
                                     LocalDateTime startingTime, LocalDateTime endingTime, String facebookLink, String instagramLink,
                                     Long categoryId, List<Long> productIds, int appointmentTime);

    void DeleteTenant(Long id);

    Optional<Tenant> UpdeteExistingTenant(Long tenantId,
                                          String name, String ownerUsername, String description, String logoUrl,
                                          String color, String address, String phoneNumber, String email, double rating,
                                          LocalDateTime startingTime, LocalDateTime endingTime, String facebookLink, String instagramLink,
                                          Long categoryId, List<Long> productIds, int appointmentTime);
    List<Category> GetAllCategories();
    List<Product> GetAllProductsForTenantCategory(Long categoryId, Long tenandId);
    boolean AddProductToTenant(Long tenantId, Long productId);

    boolean Schedule(Schedule schedule);
    List<Schedule> GetAllSchedulesForDate(Long tenantId, LocalDateTime date);
}
