package pl.coderslab.article.dao;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.coderslab.article.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDTO toDTO(Article article);

    Article toEntity(ArticleDTO articleDTO);
}
