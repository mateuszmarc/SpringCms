package pl.coderslab.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.article.ArticleDao;
import pl.coderslab.category.dto.CategoryDTO;
import pl.coderslab.category.dto.CategoryMapper;
import pl.coderslab.exception.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final CategoryDao categoryDao;
    private final ArticleDao articleDao;
    private final CategoryMapper categoryMapper;

    public CategoryDTO findById(Long id) {

        Optional<Category> optionalCategory = categoryDao.findById(id);


        return optionalCategory
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: %s not found".formatted(id)));
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {

        Category category = categoryMapper.toEntity(categoryDTO);

        categoryDao.save(category);

        return categoryMapper.toDTO(category);
    }


    public CategoryDTO update(Category category) {

        Optional<Category> optionalCategory = categoryDao.findById(category.getId());

        Category foundCategory = optionalCategory
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: %s not found".formatted(category.getId())));

        updateCategoryFields(category, foundCategory);

        categoryDao.save(foundCategory);

        return categoryMapper.toDTO(foundCategory);
    }

    public CategoryDTO deleteById(Long id) {
        Optional<Category> optionalCategory = categoryDao.findById(id);

        Category category = optionalCategory
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: %s not found".formatted(id)));

        category.getArticles().forEach(article -> {
            article.removeCategory(category);
            articleDao.save(article);
        });

        categoryDao.delete(category);
        return categoryMapper.toDTO(category);
    }


    private void updateCategoryFields(Category source, Category destination) {

        destination.setName(source.getName());
        destination.setDescription(source.getDescription());
        destination.setArticles(source.getArticles());
    }
}
