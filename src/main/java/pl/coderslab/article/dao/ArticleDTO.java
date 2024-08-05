package pl.coderslab.article.dao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDTO {

    private String title;

    private LocalDateTime created;

    private String content;
}
