package project.springservice.project_wp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @Column(length =999999)
    private String imageUrl;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private List<Tenant> tenants;

    @Enumerated(EnumType.STRING)
    private ProductEnum productEnum;

    public Product() {
        this.tenants = new ArrayList<>();
    }

    public Product(String name, double price, Category category, String imageUrl, ProductEnum productEnum) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.productEnum = productEnum;
    }
}
