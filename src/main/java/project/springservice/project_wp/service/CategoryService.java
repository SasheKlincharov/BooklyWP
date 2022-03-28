package project.springservice.project_wp.service;

import project.springservice.project_wp.model.Category;
import project.springservice.project_wp.model.CategoryEnum;
import project.springservice.project_wp.model.Product;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    List<CategoryEnum> getAllCategoryEnums();

}
