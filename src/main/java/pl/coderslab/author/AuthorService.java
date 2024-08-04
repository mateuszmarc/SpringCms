package pl.coderslab.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.article.ArticleDao;
import pl.coderslab.exception.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthorService {

    private final AuthorDao authorDao;
    private final ArticleDao articleDao;

    public Author findById(Long id) {

        Optional<Author> optionalAuthor = authorDao.findById(id);

        return optionalAuthor.orElseThrow(() -> new ResourceNotFoundException("Author with id: %s not found".formatted(id)));
    }


    public Author save(Author author) {
        authorDao.save(author);
        return author;
    }

    public Author update(Author author) {

        Author foundAuthor = findById(author.getId());

        updateAuthorFields(author, foundAuthor);

        authorDao.save(foundAuthor);
        return foundAuthor;
    }

    public Author deleteById(Long id) {

        Author author = findById(id);

        author.getArticles().forEach(article ->{
            article.setAuthor(null);
            articleDao.save(article);
        });

        authorDao.delete(author);
        return author;
    }


    private void updateAuthorFields(Author source, Author destination) {

        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setArticles(source.getArticles());
    }
}
