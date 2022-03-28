package project.springservice.project_wp.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import project.springservice.project_wp.model.User;
import project.springservice.project_wp.model.exceptions.InvalidArgumentsException;
import project.springservice.project_wp.model.exceptions.InvalidUserCredentialsException;
import project.springservice.project_wp.repository.UserRepository;
import project.springservice.project_wp.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }

}
