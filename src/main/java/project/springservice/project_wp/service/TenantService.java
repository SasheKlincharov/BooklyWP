package project.springservice.project_wp.service;

import project.springservice.project_wp.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    boolean Schedule(Long scheduleId, User user);
    List<Schedule> GetAllSchedulesForDate(Long tenantId, LocalDateTime date);

    List<Schedule> getSchedulesForTenant(Long id);

    Tenant getTenantFromSchedule(Long scheduleId);

    List<Tenant> getRandomTenants(Random rnd);
}
