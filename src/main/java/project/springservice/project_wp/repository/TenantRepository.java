package project.springservice.project_wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.springservice.project_wp.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
