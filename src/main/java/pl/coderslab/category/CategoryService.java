package pl.coderslab.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.article.ArticleDao;
import pl.coderslab.exception.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final CategoryDao categoryDao;
    private final ArticleDao articleDao;

    public Category findById(Long id) {

        Optional<Category> category = categoryDao.findById(id);


        return category
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: %s not found".formatted(id)));
    }

    public Category save(Category category) {


        log.info("Category to save: {}", category);

        categoryDao.save(category);

        return category;
    }


    public Category update(Category category) {


        Category foundCategory = findById(category.getId());

        updateCategoryFields(category, foundCategory);

        categoryDao.save(foundCategory);

        return foundCategory;
    }

    public Category deleteById(Long id) {
        Category category = findById(id);

        category.getArticles().forEach(article -> {
            article.removeCategory(category);
            articleDao.save(article);
        });

        categoryDao.delete(category);
        return category;
    }

    public List<Category> getAllCategories() {

        return categoryDao.findAllCategories();
    }

    private void updateCategoryFields(Category source, Category destination) {

        destination.setName(source.getName());
        destination.setDescription(source.getDescription());
        destination.setArticles(source.getArticles());
    }
}
