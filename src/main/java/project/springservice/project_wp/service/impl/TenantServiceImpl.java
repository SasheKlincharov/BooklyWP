package project.springservice.project_wp.service.impl;

import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import project.springservice.project_wp.model.*;
import project.springservice.project_wp.model.exceptions.*;
import project.springservice.project_wp.repository.*;
import project.springservice.project_wp.service.TenantService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TenantServiceImpl implements TenantService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public TenantServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, TenantRepository tenantRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
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

    @Override
    @Transactional
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

        List<Schedule> schedulesList = createSchedulesForTenant(startingTime,
                endingTime,
                appointmentTime,
                tenant);

        tenant.setSchedules(schedulesList);

        tenantRepository.save(tenant);
        return Optional.of(tenant);
    }

    private List<Schedule> createSchedulesForTenant(LocalDateTime startingTime, LocalDateTime endingTime, int appointmentTime, Tenant tenant) {
        int hoursDifference = endingTime.getHour() - startingTime.getHour();//16-8 = 8
        double appointmentToHour = appointmentTime/60.0;

        double totalSchedules = Math.floor(hoursDifference/appointmentToHour);
        List<Schedule> schedules = new ArrayList<>();

        int pomHour = startingTime.getHour();
        int pomMinute = startingTime.getMinute();


        for (int i = 0; i < totalSchedules; i++) {
            int tempHour = pomHour;
            int tempMinute = pomMinute;
            Schedule schedule = new Schedule(
                    LocalDateTime.of(startingTime.getYear(), startingTime.getMonth(), startingTime.getDayOfMonth(), tempHour,tempMinute),
                    LocalDateTime.of(startingTime.getYear(), startingTime.getMonth(), startingTime.getDayOfMonth(), (tempMinute+appointmentTime>=60)?tempHour+1:tempHour, (tempMinute+appointmentTime>=60)? (tempMinute+appointmentTime)%60:appointmentTime+pomMinute)
                    ,tenant,false);

            schedules.add(schedule);
            pomHour = (tempMinute+appointmentTime>=60)?tempHour+1:tempHour;
            pomMinute = (tempMinute+appointmentTime>=60)? (tempMinute+appointmentTime)%60:appointmentTime+pomMinute;
        }
        return schedules;
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
    public boolean Schedule(Long scheduleId, User user) {
        Schedule schedule = this.scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        Tenant tenant = GetTenant(schedule.getTenant().getId());

        List<Schedule> allSchedules = tenant.getSchedules();

        schedule.setScheduled(true);
        schedule.setUser(user);

        int indexOf = allSchedules.indexOf(schedule);
        allSchedules.remove(indexOf);
        allSchedules.add(indexOf, schedule);

        tenant.setSchedules(allSchedules);

        this.tenantRepository.save(tenant);
        return true;
    }

    @Override
    public List<Schedule> GetAllSchedulesForDate(Long tenantId, LocalDateTime date) {
        return null;
    }

    @Override
    public List<Schedule> getSchedulesForTenant(Long id) {
        Tenant tenant = this.GetTenant(id);

        return tenant.getSchedules();

    }

    @Override
    public Tenant getTenantFromSchedule(Long scheduleId) {
        Schedule schedule = this.scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        return schedule.getTenant();

    }

    @Override
    public List<Tenant> getRandomTenants(Random rnd) {
        List<Tenant> tenantRandoms = new ArrayList<>();
        List<Tenant> tenants = this.GetAllTenants();
        if(tenants.size()>3){
            int counter = 0;
            while(counter != 3){
                int number = rnd.nextInt(tenants.size()-1);
                if(!tenantRandoms.contains(tenants.get(number))){
                    tenantRandoms.add(tenants.get(number));
                    counter++;
                }
            }
        }
        return tenantRandoms;
    }
}
