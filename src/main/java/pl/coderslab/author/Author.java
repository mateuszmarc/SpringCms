package pl.coderslab.author;

import lombok.Data;
import lombok.ToString;
import pl.coderslab.article.Article;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<Article> articles = new ArrayList<>();

}
