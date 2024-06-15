package pl.coderslab.author;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Author author) {
        entityManager.persist(author);
    }

    public Optional<Author> findById(long id) {
        Optional<Author> optionalAuthor = Optional.ofNullable(entityManager.find(Author.class, id));

        return optionalAuthor.map(author -> {
            Hibernate.initialize(author.getArticles());
            return Optional.of(author);
        }).orElseGet(Optional::empty);

    }

    public void update(Author author) {
        entityManager.merge(author);
    }

    public void deleteById(long id) {
        Optional<Author> optionalAuthor = findById(id);

        optionalAuthor.ifPresent(author -> entityManager.remove(author));
    }
}
