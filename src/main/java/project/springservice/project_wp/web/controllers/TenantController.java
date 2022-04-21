package project.springservice.project_wp.web.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.springservice.project_wp.model.*;
import project.springservice.project_wp.service.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/tenants")
public class TenantController  {

    private final TenantService tenantService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final AuthService authService;

    public TenantController(TenantService tenantService, ProductService productService, CategoryService categoryService, UserService userService, AuthService authService) {
        this.tenantService = tenantService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.authService = authService;
    }


    @GetMapping
    public String getTenantPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Tenant> tenants = this.tenantService.GetAllTenants();
        model.addAttribute("tenants", tenants);
        model.addAttribute("bodyContent", "tenants");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTenant(@PathVariable Long id) {
        this.tenantService.DeleteTenant(id);
        return "redirect:/tenants";
    }

    @GetMapping("/edit-form/{id}")
    public String editTenantPage(@PathVariable Long id, Model model) {
        if (this.tenantService.GetTenant(id) != null) {
            Tenant tenant = this.tenantService.GetTenant(id);
            return getString(model);
        }
        return "redirect:/tenants?error=TenantNotFound";
    }

    private String getString(Model model) {
        List<Category> categories = this.tenantService.GetAllCategories();
        List<Product> products = this.productService.GetAllProducts();
        List<User> users = this.userService.GetAllUsers();

        model.addAttribute("users",users);
        model.addAttribute("categories", categories);
        model.addAttribute("products",products);
        model.addAttribute("bodyContent", "add-tenant");

        return "master-template";
    }

    @GetMapping("/add-form")
    public String addTenantPage(Model model) {
//        List<Schedule> manufacturers = this.tenantService.GetAllSchedulesForDate();
        return getString(model);
    }

    @PostMapping("/add")
    public String saveTenant(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String ownerUsername,
            @RequestParam String logoUrl,
            @RequestParam String address,
            @RequestParam String phoneNumber,
            @RequestParam String email,
            @RequestParam String facebookLink,
            @RequestParam String instagramLink,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startingTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endingTime,
            @RequestParam Long categoryId,
            @RequestParam List<Long> productIds,
            @RequestParam int appointmentTime) {
        if (id != null) {
            this.tenantService.UpdeteExistingTenant(id, name, ownerUsername, description, logoUrl,
                    "Green", address, phoneNumber, email, 10.0,
            startingTime, endingTime, facebookLink, instagramLink,
                    categoryId, productIds, appointmentTime);
        } else {
            this.tenantService.CreateNewTenant( name, ownerUsername, description, logoUrl,
                    "Green", address, phoneNumber, email, 10.0,
                    startingTime, endingTime, facebookLink, instagramLink,
                    categoryId, productIds, appointmentTime);
        }
        return "redirect:/tenants";
    }

    @GetMapping("/schedules/tenant_{id}")
    public String getTenantSchedules(@PathVariable Long id,
                                     @RequestParam(required = false) String error,
                                     Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Schedule> scheduleList = this.tenantService.getSchedulesForTenant(id);
        scheduleList.sort(Comparator.comparing(Schedule::getStatus).reversed());

        Tenant tenant = this.tenantService.GetTenant(id);
        model.addAttribute("schedules", scheduleList);
        model.addAttribute("tenant", tenant);

        model.addAttribute("bodyContent", "schedules");
        return "master-template";
    }

    @Transactional
    @PostMapping("/schedule/{id}")
    public String scheduleForTenant(@PathVariable Long id,
                                    Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        this.tenantService.Schedule(id, user);

        Tenant tenant = this.tenantService.getTenantFromSchedule(id);
        List<Schedule> scheduleList = this.tenantService.getSchedulesForTenant(tenant.getId());
        scheduleList.sort(Comparator.comparing(Schedule::getStatus).reversed());
        model.addAttribute("schedules", scheduleList);
        model.addAttribute("tenant", tenant);

        model.addAttribute("bodyContent", "schedules");
        return "master-template";
    }
}
