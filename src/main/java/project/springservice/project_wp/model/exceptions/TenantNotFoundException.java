package project.springservice.project_wp.model.exceptions;

public class TenantNotFoundException extends RuntimeException{
    public TenantNotFoundException(Long id) {
        super(String.format("Tenant with id: %d is not found", id));
    }
}
