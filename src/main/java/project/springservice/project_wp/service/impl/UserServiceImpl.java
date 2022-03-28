package project.springservice.project_wp.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.springservice.project_wp.model.Tenant;
import project.springservice.project_wp.model.User;
import project.springservice.project_wp.model.exceptions.InvalidUsernameOrPasswordException;
import project.springservice.project_wp.model.exceptions.PasswordsDoNotMatchException;
import project.springservice.project_wp.model.exceptions.TenantNotFoundException;
import project.springservice.project_wp.model.exceptions.UsernameAlreadyExistsException;
import project.springservice.project_wp.repository.TenantRepository;
import project.springservice.project_wp.repository.UserRepository;
import project.springservice.project_wp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TenantRepository tenantRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }


    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Long tenantId) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();

        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();

        if (this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);

        User user;
        if(tenantId!=null) {
            Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new TenantNotFoundException(tenantId));
            user = new User(username, passwordEncoder.encode(password), name, surname, tenant);
        }else {
            user = new User(username, passwordEncoder.encode(password), name, surname);
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> GetAllUsers() {
        return this.userRepository.findAll();
    }


}
