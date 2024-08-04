package pl.coderslab.article;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.article.dto.ArticleDTO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{id}")
    public ArticleDTO findArticleById(@PathVariable Long id) {
        return articleService.findArticleById(id);
    }

    @PostMapping
    public Article saveArticle(@RequestBody ArticleDTO articleDTO) {

        return articleService.save(articleDTO);
    }

//    @PutMapping
//    public Article updateArticle(@RequestBody Article article) {
//
//        return articleService.update(article);
//    }

    @DeleteMapping("/{id}")
    public Article deleteArticleById(@PathVariable Long id) {

        return  articleService.deleteById(id);
    }
}
