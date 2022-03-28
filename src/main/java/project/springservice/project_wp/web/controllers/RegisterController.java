package project.springservice.project_wp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.springservice.project_wp.model.Tenant;
import project.springservice.project_wp.model.exceptions.InvalidArgumentsException;
import project.springservice.project_wp.model.exceptions.PasswordsDoNotMatchException;
import project.springservice.project_wp.service.TenantService;
import project.springservice.project_wp.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final TenantService tenantService;

    public RegisterController(UserService userService, TenantService tenantService) {
        this.userService = userService;
        this.tenantService = tenantService;
    }


    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Tenant> tenants = this.tenantService.GetAllTenants();
        model.addAttribute("tenants", tenants);
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam(required = false) Long tenantOwner) {
        try {
            this.userService.register(username, password, repeatedPassword, name, surname, tenantOwner);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
