package pl.coderslab.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);

        return ResponseEntity.ok(category.toString());
    }

    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody Category categoryDTO) {

        Category category = categoryService.save(categoryDTO);

        return ResponseEntity.ok(category.toString());
    }

    @PutMapping
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {

        Category updatadtedCategory = categoryService.update(category);

        return ResponseEntity.ok(updatadtedCategory.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        Category category = categoryService.deleteById(id);

        return ResponseEntity.ok(category.toString());
    }
}
