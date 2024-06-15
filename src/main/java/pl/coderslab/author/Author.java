package pl.coderslab.author;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.article.Article;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
    private List<Article> articles;

}
