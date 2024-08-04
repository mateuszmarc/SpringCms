package pl.coderslab.article;

import lombok.Data;
import pl.coderslab.author.AuthorDTO;
import pl.coderslab.category.CategoryDTO;

import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private AuthorDTO author;
    private List<CategoryDTO> categories;
}

