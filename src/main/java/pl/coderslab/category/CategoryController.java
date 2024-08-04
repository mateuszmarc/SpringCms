package pl.coderslab.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.category.dto.CategoryDTO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public CategoryDTO saveCategory(@RequestBody CategoryDTO categoryDTO) {

        return categoryService.save(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public CategoryDTO deleteById(@PathVariable Long id) {

        return categoryService.deleteById(id);
    }
}
