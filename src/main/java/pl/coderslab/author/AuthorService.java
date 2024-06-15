package pl.coderslab.author;

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
public class AuthorService {

    private final AuthorDao authorDao;
    private final ArticleDao articleDao;

    public void addAuthor(Author author) {
        authorDao.save(author);

        author.getArticles().forEach(article -> {
            article.setAuthor(author);
            articleDao.update(article);
        });
    }

    public void updateAuthor(Author author) {
        Optional<Author> optionalAuthor = authorDao.findById(author.getId());

        if (optionalAuthor.isPresent()) {
            Author notUpdatedAuthor = optionalAuthor.get();

            List<Article> notUpdatedAuthorArticles = notUpdatedAuthor.getArticles();
            List<Article> updatedAuthorArticles = author.getArticles();

            updatedAuthorArticles.forEach(article -> {
                if (!notUpdatedAuthorArticles.contains(article)) {
                    article.setAuthor(author);
                    articleDao.update(article);
                }
            });

            notUpdatedAuthorArticles.forEach(article -> {
                if (!updatedAuthorArticles.contains(article)) {
                    article.setAuthor(null);
                    articleDao.update(article);
                }
            });
        }
    }

    public Optional<Author> getAuthorById(long id) {
        return authorDao.findById(id);
    }

    public void deleteAuthorById(long id) {
        Optional<Author> optionalAuthor = authorDao.findById(id);

        optionalAuthor.ifPresent(author -> {
            author.getArticles().forEach(article -> {
                article.setAuthor(null);
                articleDao.update(article);
            });
            authorDao.deleteById(id);
        });
    }
}
