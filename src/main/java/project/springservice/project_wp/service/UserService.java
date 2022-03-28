package project.springservice.project_wp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import project.springservice.project_wp.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> GetAllUsers();

    User register(String username, String password, String repeatPassword, String name, String surname, Long tenantId);

}
