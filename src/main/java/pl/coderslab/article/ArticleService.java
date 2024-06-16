package pl.coderslab.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.author.Author;
import pl.coderslab.author.AuthorDao;
import pl.coderslab.category.Category;
import pl.coderslab.category.CategoryDao;
import pl.coderslab.customExceptions.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleDao articleDao;
    private final AuthorDao authorDao;
    private final CategoryDao categoryDao;

    public void saveArticle(Article article) {

        updateAuthorForArticle(article);
        
        updateCategoriesForArticle(article);

        articleDao.save(article);
    }
    
    private void updateAuthorForArticle(Article article) {
        if (doesArticleHaveAuthorSet(article)) {

            Long authorId = article.getAuthor().getId();

            Optional<Author> optionalAuthor = authorDao.findById(authorId);

            if (isAuthorWithIdInDatabase(optionalAuthor)) {

                Author authorFromDatabase = optionalAuthor.get();
                addArticleForAuthor(article, authorFromDatabase);
            } else {
                throw new ResourceNotFoundException("Author with authorId: %s not found".formatted(authorId));
            }
        }
    }

    private boolean doesArticleHaveAuthorSet(Article article) {
        return article.getAuthor() != null;
    }

    private static boolean isAuthorWithIdInDatabase(Optional<Author> optionalAuthor) {
        return optionalAuthor.isPresent();
    }

    private void addArticleForAuthor(Article article, Author authorFromDatabase) {
        article.setAuthor(authorFromDatabase);

        authorDao.update(authorFromDatabase);
    }

    private void updateCategoriesForArticle(Article article) {
        article.getCategories().forEach(category -> {
            Long categoryId = category.getId();
            Optional<Category> optionalCategory = categoryDao.findById(categoryId);

            if (isCategoryInDatabase(optionalCategory)) {
                addArticleToCategory(article, category, optionalCategory);
            } else {
                throw new ResourceNotFoundException("Category with id: %s not found".formatted(categoryId));
            }
        });
    }

    private static boolean isCategoryInDatabase(Optional<Category> optionalCategory) {
        return optionalCategory.isPresent();
    }
    
    private void addArticleToCategory(Article article, Category category, Optional<Category> optionalCategory) {
        Category retirevedCategory = optionalCategory.get();

        retirevedCategory.addArticle(article);

        category.setName(retirevedCategory.getName());
        category.setDescription(retirevedCategory.getDescription());
        category.setArticles(retirevedCategory.getArticles());

        categoryDao.update(category);
    }
    
}
