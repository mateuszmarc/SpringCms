package pl.coderslab.author.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.coderslab.author.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDTO authorDTO);
}
