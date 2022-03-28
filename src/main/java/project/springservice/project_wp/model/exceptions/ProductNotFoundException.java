package project.springservice.project_wp.model.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super(String.format("Product with id: %d is not found", id));
    }
}
