package pl.coderslab.category;

import lombok.Data;
import pl.coderslab.article.Article;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "categories",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            },
            fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();


    private void addArticle(Article article) {
        articles.removeIf(article1 -> article1.getId().equals(article.getId()));

        articles.add(article);
    }

    public void removeArticle(Article article) {

        articles.removeIf(article1 -> article1.getId().equals(article.getId()));
    }
}
