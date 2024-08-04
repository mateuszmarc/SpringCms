package pl.coderslab.category.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.coderslab.article.dto.ArticleMapper;
import pl.coderslab.category.Category;

@Mapper(componentModel = "spring", uses = ArticleMapper.class)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);
}
