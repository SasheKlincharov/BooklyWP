package project.springservice.project_wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.springservice.project_wp.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
