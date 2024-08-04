package pl.coderslab.article;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ArticleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Article article) {
        entityManager.merge(article);
    }

    public Optional<Article> findById(long id) {

        return Optional.ofNullable(entityManager.find(Article.class, id));

    }

    public void delete(Article article) {
        entityManager.remove(article);
    }

}
