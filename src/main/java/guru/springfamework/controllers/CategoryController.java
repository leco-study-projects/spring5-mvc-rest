package guru.springfamework.controllers;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.contracts.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }


    @GetMapping("/{name}")
    public ResponseEntity<CategoryDTO> findByName(@PathVariable String name) {
        CategoryDTO category = this.service.getCategoryByName(name);

        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<CategoryDTO> categories = this.service.getAllCategories();

        if (categories == null || categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categories);
    }

}
