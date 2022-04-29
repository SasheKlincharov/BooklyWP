package project.springservice.project_wp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private User owner;
    @Column(length = 999999)
    private String description;
    @Column(length = 999999)
    private String logoUrl;
    private String color;
    private String address;
    private String phoneNumber;
    private String email;
    private double rating;
    @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy")
    private LocalDateTime startingTime;
    @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy")
    private LocalDateTime endingTime;
    private String facebookLink;
    private String instagramLink;
    @ManyToOne
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Schedule> schedules;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private List<Product> productsInTenant;

    public int appointmentTime;

    public Tenant(){}

    public Tenant(String name, User owner, String description, String logoUrl, String color, String address, String phoneNumber, String email, double rating, LocalDateTime startingTime, LocalDateTime endingTime, String facebookLink, String instagramLink, Category category, List<Product> productsInTenant, int appointmentTime) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.logoUrl = logoUrl;
        this.color = color;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.rating = rating;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.facebookLink = facebookLink;
        this.instagramLink = instagramLink;
        this.category = category;
        this.productsInTenant = productsInTenant;
        this.appointmentTime = appointmentTime;
        this.schedules = new ArrayList<>();
    }
}
