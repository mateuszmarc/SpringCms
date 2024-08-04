package pl.coderslab.category;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Category category) {
        entityManager.merge(category);
    }

    public Optional<Category> findById(long id) {
        return Optional.ofNullable(entityManager.find(Category.class, id));
    }

    public void delete(Category category) {

        entityManager.remove(category);
    }
}
