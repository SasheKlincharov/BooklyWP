package project.springservice.project_wp.model.exceptions;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id: %d is not found", id));
    }

    public CategoryNotFoundException(String name){
        super(String.format("Category with name: %s is not found", name));

    }

}
