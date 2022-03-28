package project.springservice.project_wp.service.impl;

import org.springframework.stereotype.Service;
import project.springservice.project_wp.model.Category;
import project.springservice.project_wp.model.CategoryEnum;
import project.springservice.project_wp.repository.CategoryRepository;
import project.springservice.project_wp.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryEnum> getAllCategoryEnums() {
        return Stream.of(CategoryEnum.values()).collect(Collectors.toList());
    }


}
