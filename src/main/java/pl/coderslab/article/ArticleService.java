package pl.coderslab.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.author.Author;
import pl.coderslab.author.AuthorDao;
import pl.coderslab.category.CategoryDao;
import pl.coderslab.exception.InvalidRequestException;
import pl.coderslab.exception.ResourceNotFoundException;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleDao articleDao;
    private final AuthorDao authorDao;
    private final CategoryDao categoryDao;

    public Article findArticleById(Long id) {

        Optional<Article> article = articleDao.findById(id);

        return article
                .orElseThrow(() -> new ResourceNotFoundException("Article with id: %s not found".formatted(id)));
    }

    public Article save(Article article) {
        if (article.getId() != null) {
            throw new InvalidRequestException("Article to save should not have its id set");
        }

        articleDao.save(article);

        return article;
    }

    public Article deleteById(Long id) {

        Article article = findArticleById(id);

        Author author = article.getAuthor();

        if (author != null) {
            article.getAuthor().removeArticle(article);
            authorDao.save(article.getAuthor());

        }

        article.getCategories().forEach(category -> {
            category.removeArticle(article);
            categoryDao.save(category);
        });

        articleDao.delete(article);
        return article;
    }


    public Article update(Article article) {

        Article foundArticle = findArticleById(article.getId());

        updateArticleFields(article, foundArticle);

        articleDao.save(foundArticle);

        return foundArticle;
    }


    private void updateArticleFields(Article source, Article destination) {

        destination.setTitle(source.getTitle());
        destination.setContent(source.getContent());
        destination.setAuthor(source.getAuthor());
        destination.setCategories(source.getCategories());
    }


}
