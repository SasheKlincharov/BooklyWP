package project.springservice.project_wp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.springservice.project_wp.model.Tenant;
import project.springservice.project_wp.service.TenantService;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final TenantService tenantService;

    public HomeController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public String getHomePage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Tenant> tenants = this.tenantService.GetAllTenants();
        model.addAttribute("tenants", tenants);
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }
}
