package pl.coderslab.article;

import lombok.Data;
import lombok.ToString;
import pl.coderslab.author.Author;
import pl.coderslab.category.Category;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 250)
    private String title;

    private String content;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    },
            fetch = FetchType.EAGER)
    @JoinTable(name = "articles_categories",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    private List<Category> categories = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }


    public void addCategory(Category category) {
        removeCategory(category);
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.removeIf(category1 -> category1.getId().equals(category.getId()));
    }
}
