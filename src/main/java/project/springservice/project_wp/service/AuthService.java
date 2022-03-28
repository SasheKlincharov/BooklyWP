package project.springservice.project_wp.service;

import project.springservice.project_wp.model.User;

public interface AuthService {
    User login(String username, String password);
}
