package pl.coderslab.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.article.ArticleDao;
import pl.coderslab.exception.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final CategoryDao categoryDao;
    private final ArticleDao articleDao;

    public Category findById(Long id) {

        Optional<Category> optionalCategory = categoryDao.findById(id);

        return optionalCategory
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: %s not found".formatted(id)));
    }

    public Category save(Category category) {

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


    private void updateCategoryFields(Category source, Category destination) {

        destination.setName(source.getName());
        destination.setDescription(source.getDescription());
        destination.setArticles(source.getArticles());
    }
}
