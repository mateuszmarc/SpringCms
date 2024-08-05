package pl.coderslab.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<String> findAllArticles() {
        List<Article> articles = articleService.findAllArticles();

        return ResponseEntity.ok(articles.toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findArticleById(@PathVariable Long id) {
        Article article =  articleService.findArticleById(id);

        return ResponseEntity.ok(article.toString());
    }

    @PostMapping
    public ResponseEntity<String> saveArticle(@RequestBody Article article) {

        Article savedArticle =  articleService.save(article);

        return ResponseEntity.ok(savedArticle.toString());
    }

    @PutMapping
    public  ResponseEntity<String>  updateArticle(@RequestBody Article article) {

        Article updatedArticle = articleService.update(article);
        return ResponseEntity.ok(updatedArticle.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticleById(@PathVariable Long id) {

        Article deletedArticle = articleService.deleteById(id);

        return ResponseEntity.ok(deletedArticle.toString());
    }
}
