package pl.coderslab.category.dto;

import lombok.Data;
import pl.coderslab.article.dto.ArticleDTO;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private List<ArticleDTO> articles;
}

