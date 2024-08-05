package pl.coderslab.author;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Author author) {
        entityManager.merge(author);
    }

    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    public void delete(Author author) {

        entityManager.remove(author);
    }

    public List<Author> findAll() {

        TypedQuery<Author> query = entityManager.createQuery("SELECT a FROM Author a", Author.class);

        return query.getResultList();
    }
}
