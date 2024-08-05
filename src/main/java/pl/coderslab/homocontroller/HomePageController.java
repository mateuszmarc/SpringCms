package pl.coderslab.homocontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.article.ArticleService;
import pl.coderslab.article.dao.ArticleDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HomePageController {

    private final ArticleService articleService;

    @GetMapping("/")
    public ResponseEntity<String> findLastFiveArticles() {

        List<ArticleDTO> articles = articleService.findLastFiveArticles();

        return ResponseEntity.ok(articles.toString());

    }
}
