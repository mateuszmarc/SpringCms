package pl.coderslab.article;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class ArticleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Article article) {
        entityManager.persist(article);
    }

    public Optional<Article> findById(long id) {
        Optional<Article> optionalArticle = Optional.ofNullable(entityManager.find(Article.class, id));

        return optionalArticle.map(article -> {
            Hibernate.initialize(article.getCategories());
            return Optional.of(article);
        }).orElseGet(Optional::empty);

    }

    public void update(Article article) {
        entityManager.merge(article);
    }

    public void deleteById(long id) {
        Optional<Article> optionalArticle = findById(id);

        optionalArticle.ifPresent(article -> entityManager.remove(article));
    }

}
