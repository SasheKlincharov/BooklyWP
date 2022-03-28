package project.springservice.project_wp.bootstrap;


import org.springframework.stereotype.Component;
import project.springservice.project_wp.model.*;
import project.springservice.project_wp.repository.CategoryRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component // za singleton instanca
public class DataHolder {
    public static List<Category> categories = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static List<Schedule> schedules = new ArrayList<>();
    public static List<Tenant> tenants = new ArrayList<>();
    public DataHolder(){
    }

//    @PostConstruct // odma da se povika init za da se popolnat categories
//    public void init(){
//        categories.add(new Category("MASSAGER", CategoryEnum.MASSAGER));
//        categories.add(new Category("HAIRDRESSER", CategoryEnum.HAIRDRESSER));
//        categories.add(new Category("MAKEUP_ARTIST", CategoryEnum.MAKEUP_ARTIST));
//
//        categoryRepository.saveAll(categories);
//
//    }
}