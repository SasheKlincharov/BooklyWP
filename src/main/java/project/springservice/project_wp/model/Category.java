package project.springservice.project_wp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    public Category() {
    }

    public Category(String name, CategoryEnum categoryEnum) {
        this.name = name;
        this.categoryEnum = categoryEnum;
    }
}
