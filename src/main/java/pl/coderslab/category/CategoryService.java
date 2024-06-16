package pl.coderslab.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.article.Article;
import pl.coderslab.article.ArticleDao;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;
    private final ArticleDao articleDao;

    private void saveCategory(Category category) {
        categoryDao.save(category);
        category.getArticles().forEach(
                article -> {
                    article.getCategories().add(category);
                    articleDao.update(article);
                }
        );
    }

    private void updateCategory(Category category) {
        Optional<Category> optionalCategory = categoryDao.findById(category.getId());
        categoryDao.update(category);

        if (optionalCategory.isPresent()) {
            Category notUpdatedCategory = optionalCategory.get();
            List<Article> notUpdatedCategoryArticles = notUpdatedCategory.getArticles();
            List<Article> updatedCategoryArticles = category.getArticles();

            updatedCategoryArticles.forEach(article -> {
                if (!notUpdatedCategoryArticles.contains(article)) {
                    article.getCategories().add(category);
                    articleDao.update(article);
                }
            });

            notUpdatedCategoryArticles.forEach(article -> {
                if (!updatedCategoryArticles.contains(article)) {
                    article.getCategories().remove(category);
                    articleDao.update(article);
                }
            });
        }
    }

    private Optional<Category> findCategoryById(long id) {
        return categoryDao.findById(id);
    }

    private void deleteCategory(long id) {
        Optional<Category> optionalCategory = findCategoryById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.getArticles().forEach(article -> {
                article.getCategories().remove(category);
                articleDao.update(article);
            });
            categoryDao.deleteById(id);
        }
    }
}