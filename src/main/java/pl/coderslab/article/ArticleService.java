package pl.coderslab.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.author.Author;
import pl.coderslab.author.AuthorDao;
import pl.coderslab.category.Category;
import pl.coderslab.category.CategoryDao;
import pl.coderslab.customExceptions.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleDao articleDao;
    private final AuthorDao authorDao;
    private final CategoryDao categoryDao;

    private static void setCategoryFieldsForUpdate(Article article, Category category, Category retrievedCategory) {
        retrievedCategory.addArticle(article);

        category.setName(retrievedCategory.getName());
        category.setDescription(retrievedCategory.getDescription());
        category.setArticles(retrievedCategory.getArticles());
    }

    public Article saveArticle(Article article) {

        updateAuthorForArticle(article);

        updateCategoriesForArticle(article);

        articleDao.save(article);
        return article;
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

        setCategoryFieldsForUpdate(article, category, retirevedCategory);

        categoryDao.update(category);
    }


    public Article updateArticle(Article article) {

        Long articleId = article.getId();
        Optional<Article> optionalArticle = articleDao.findById(articleId);

        if (isArticleWithIdInDatabase(optionalArticle)) {
            Article retrievedArticle = optionalArticle.get();

            article.setCreated(retrievedArticle.getCreated());

            updateAuthorForArticle(article, retrievedArticle);

            updateCategoriesForArticle(article, retrievedArticle);

            articleDao.update(article);
            return findArticleById(articleId);
        } else {
            throw new ResourceNotFoundException("Article with id: %s not found".formatted(articleId));
        }
    }

    private boolean isArticleWithIdInDatabase(Optional<Article> optionalArticle) {
        return optionalArticle.isPresent();
    }

    private void updateAuthorForArticle(Article updatedArticle, Article oldArticle) {
        Long updatedAuthorId = updatedArticle.getAuthor().getId();

        if (!updatedAuthorId.equals(oldArticle.getAuthor().getId())) {
            Optional<Author> optionalUpdatedAuthor = authorDao.findById(updatedAuthorId);

            if (optionalUpdatedAuthor.isPresent()) {
                Author updatedAuthor = optionalUpdatedAuthor.get();
                Author oldAuthor = oldArticle.getAuthor();

                oldAuthor.removeArticle(oldArticle);
                authorDao.update(oldAuthor);

                updatedArticle.setAuthor(updatedAuthor);
                authorDao.update(updatedAuthor);
            } else {
                throw new ResourceNotFoundException("Could not update Article. Author with id: %s not found".formatted(updatedAuthorId));
            }
        }
    }

    private void updateCategoriesForArticle(Article updatedArticle, Article oldArticle) {
        List<Category> retrievedArticleCategories = oldArticle.getCategories();
        List<Category> updatedArticleCategories = updatedArticle.getCategories();

        addArticleToCategories(updatedArticle, updatedArticleCategories, retrievedArticleCategories);

        removeArticleFromCategories(updatedArticle, retrievedArticleCategories, updatedArticleCategories);
    }

    private void addArticleToCategories(Article article, List<Category> updatedArticleCategories, List<Category> retrievedArticleCategories) {
        updatedArticleCategories.forEach(category -> {

            Long categoryId = category.getId();
            Optional<Category> optionalCategory = categoryDao.findById(categoryId);

            if (isCategoryInDatabase(optionalCategory)) {
                Category retrievedCategory = optionalCategory.get();
                if (!retrievedArticleCategories.contains(retrievedCategory)) {
                    setCategoryFieldsForUpdate(article, category, retrievedCategory);

                    categoryDao.update(category);
                }
            } else {
                throw new ResourceNotFoundException("Could not update Article.\nCould not find category with id: %s".formatted(categoryId));
            }
        });

    }


    private void removeArticleFromCategories(Article article, List<Category> retrievedArticleCategories, List<Category> updatedArticleCategories) {
        retrievedArticleCategories.forEach(category -> {
            if (!updatedArticleCategories.contains(category)) {
                category.removeArticle(article);
                categoryDao.update(category);
            }
        });
    }


    public Article findArticleById(long id) {
        Optional<Article> optionalArticle = articleDao.findById(id);

        return optionalArticle.orElseThrow(() -> new ResourceNotFoundException("Article with id: %s not found".formatted(id)));
    }


    public Article deleteArticleById(long id) {
        Optional<Article> optionalArticle = articleDao.findById(id);

        return optionalArticle.map(article -> {
            RemoveArticleForAuthor(article);

            RemoveArticleFromCategories(article);
            articleDao.deleteById(id);
            return article;
        }).orElseThrow(() -> new ResourceNotFoundException("Article with id: %s not found".formatted(id)));
    }

    private void RemoveArticleForAuthor(Article article) {
        Author author = article.getAuthor();
        if (author != null) {
            author.removeArticle(article);
            authorDao.update(author);
        }
    }

    private void RemoveArticleFromCategories(Article article) {
        article.getCategories().forEach(category -> {
            category.removeArticle(article);
            categoryDao.update(category);
        });
    }

}
