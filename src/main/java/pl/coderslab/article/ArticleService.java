package pl.coderslab.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.author.Author;
import pl.coderslab.author.AuthorDao;
import pl.coderslab.category.Category;
import pl.coderslab.category.CategoryDao;

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

    public void saveArticle(Article article) {
        articleDao.save(article);

        Author author = article.getAuthor();
        if (author != null) {
            author.getArticles().add(article);
            authorDao.update(author);
        }

        article.getCategories().forEach(category -> {
            category.getArticles().add(article);
            categoryDao.update(category);
        });
    }

    public void updateArticle(Article article) {
        Optional<Article> optionalArticle = articleDao.findById(article.getId());

        if (optionalArticle.isPresent()) {
            Article notUpdatedArticle = optionalArticle.get();

            List<Category> notUpdatedArticleCategories = notUpdatedArticle.getCategories();
            List<Category> updatedArticleCategories = article.getCategories();

            updatedArticleCategories.forEach(category -> {
                if (!notUpdatedArticleCategories.contains(category)) {
                    category.getArticles().add(article);
                    categoryDao.update(category);
                }
            });

            notUpdatedArticleCategories.forEach(category -> {
                if (!updatedArticleCategories.contains(category)) {
                    category.getArticles().remove(article);
                    categoryDao.update(category);
                }
            });
        }
    }

    public Optional<Article> findArticleById(long id) {
        return articleDao.findById(id);
    }

    public void deleteArticleById(long id) {
        Optional<Article> optionalArticle = articleDao.findById(id);

        optionalArticle.ifPresent(article -> {
            Author author = article.getAuthor();
            if (author != null) {
                author.getArticles().remove(article);
                authorDao.update(author);
            }

            article.getCategories().forEach(category -> {
                category.getArticles().remove(article);
                categoryDao.update(category);
            });
        });
    }
}
