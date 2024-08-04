package pl.coderslab.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.article.dto.ArticleDTO;
import pl.coderslab.article.dto.ArticleMapper;
import pl.coderslab.author.Author;
import pl.coderslab.author.AuthorDao;
import pl.coderslab.category.CategoryDao;
import pl.coderslab.exception.InvalidRequestException;
import pl.coderslab.exception.ResourceNotFoundException;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleDao articleDao;
    private final AuthorDao authorDao;
    private final CategoryDao categoryDao;
    private final ArticleMapper articleMapper;

    public ArticleDTO findArticleById(Long id) {

        Optional<Article> article = articleDao.findById(id);

        return article.map(articleMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Article with id: %s not found".formatted(id)));
    }

    public Article save(ArticleDTO articleDTO) {
        if (articleDTO.getId() != null) {
            throw new InvalidRequestException("Article to save should not have its id set");
        }

        Article articleToSave = articleMapper.toEntity(articleDTO);


        articleDao.save(articleToSave);

        log.info("{}", articleToSave.getCategories());
        return articleToSave;
    }

    public Article deleteById(Long id) {

        Optional<Article> optionalArticle = articleDao.findById(id);

        if (optionalArticle.isEmpty()) {
            throw new ResourceNotFoundException("Article with id: %s not found".formatted(id));
        }

        Article article = optionalArticle.get();

        Author author = article.getAuthor();

        if (author != null) {
            article.getAuthor().removeArticle(article);
            authorDao.save(article.getAuthor());

        }

        article.getCategories().forEach(category -> {
            category.removeArticle(article);
            categoryDao.save(category);
        });

        return article;
    }


//    public Article update(Article article) {
//
//        Article foundArticle = findArticleById(article.getId());
//
//        updateArticleFields(article, foundArticle);
//
//        articleDao.save(foundArticle);
//
//        return foundArticle;
//    }


    private void updateArticleFields(Article source, Article destination) {

        destination.setTitle(source.getTitle());
        destination.setContent(source.getContent());
        destination.setAuthor(source.getAuthor());
        destination.setCategories(source.getCategories());
    }


}
