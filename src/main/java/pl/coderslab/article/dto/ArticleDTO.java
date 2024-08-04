package pl.coderslab.article.dto;

import lombok.Data;
import pl.coderslab.author.dto.AuthorDTO;
import pl.coderslab.category.dto.CategoryDTO;

import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private AuthorDTO author;
    private List<CategoryDTO> categories;
}

