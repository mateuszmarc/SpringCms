package pl.coderslab.category;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Category category) {
        entityManager.persist(category);
    }

    public Optional<Category> findById(long id) {
        Optional<Category> optionalCategory = Optional.ofNullable(entityManager.find(Category.class, id));

        return optionalCategory.map(category -> {
            Hibernate.initialize(category.getArticles());
            return Optional.of(category);
        }).orElseGet(Optional::empty);

    }

    public void update(Category category) {
        entityManager.merge(category);
    }

    public void deleteById(long id) {
        Optional<Category> optionalCategory = findById(id);

        optionalCategory.ifPresent(category -> entityManager.remove(category));
    }
}
