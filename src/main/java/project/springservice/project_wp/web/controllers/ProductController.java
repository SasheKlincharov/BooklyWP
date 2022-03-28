package project.springservice.project_wp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.springservice.project_wp.model.*;
import project.springservice.project_wp.service.CategoryService;
import project.springservice.project_wp.service.ProductService;
import project.springservice.project_wp.service.TenantService;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final TenantService tenantService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService,
                             TenantService tenantService, CategoryService categoryService) {
        this.productService = productService;
        this.tenantService = tenantService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Product> products = this.productService.GetAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.productService.DeleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.productService.GetProduct(id) != null) {
            Product product = this.productService.GetProduct(id);
            List<Category> categories = this.categoryService.findAll();
            List<CategoryEnum> categoryEnums = this.categoryService.getAllCategoryEnums();
            List<ProductEnum> productEnums = this.productService.getAllProductEnums();
            model.addAttribute("categories", categories);
            model.addAttribute("product", product);
            model.addAttribute("productTypes", productEnums);
            model.addAttribute("categoryTypes", categoryEnums);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }
        return "redirect:/products?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    public String addProductPage(Model model) {
        List<CategoryEnum> categories = this.categoryService.getAllCategoryEnums();
        List<Tenant> tenants = this.tenantService.GetAllTenants();
        List<ProductEnum> productEnums = this.productService.getAllProductEnums();
        model.addAttribute("categoryTypes", categories);
        model.addAttribute("tenants", tenants);
        model.addAttribute("productTypes", productEnums);
        model.addAttribute("bodyContent", "add-product");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam CategoryEnum categoryType,
            @RequestParam String imageUrl,
            @RequestParam ProductEnum productType) {
        if (id != null) {
            this.productService.UpdeteExistingProduct(id, name, price, categoryType, imageUrl, productType);
        } else {
            this.productService.CreateNewProduct(name, price, categoryType, imageUrl, productType);
        }
        return "redirect:/products";
    }

}
