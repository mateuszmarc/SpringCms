package pl.coderslab.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleDao articleDao;

    @PostMapping
    public ResponseEntity<String> addArticle(@RequestBody Article article) {
        log.info("Article from request: %s".formatted(article));
       Article article1 =  articleService.saveArticle(article);
        return ResponseEntity.ok("Saved article: %s".formatted(article1));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getArticleForId(@PathVariable long id) {
        Article article = articleService.findArticleById(id);

        return ResponseEntity.ok("Article: %s".formatted(article));
    }

    @PutMapping
    public ResponseEntity<String> updateArticle(@RequestBody Article article) {
        Article updatedArticle = articleService.updateArticle(article);

        return ResponseEntity.ok("Updated article: %s".formatted(updatedArticle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
       Article deletedArticle = articleService.deleteArticleById(id);
        return ResponseEntity.ok("Deleted article: %s".formatted(deletedArticle));
    }
}
