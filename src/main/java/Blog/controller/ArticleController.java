package Blog.controller;

import Blog.model.Article;
import Blog.model.ArticlePaginated;
import Blog.model.ImageDetails;
import Blog.request.ArticlePostRequest;
import Blog.request.CommentRequest;
import Blog.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllArticles(@Param("page") int page, @Param("size") int size) {
        var articles = articleService.getAllArticles(page, size);
        return ResponseEntity.ok(ArticlePaginated.toArticle(articles));
    }

    @PostMapping("save")
    public ResponseEntity<String> saveArticle(@Valid @RequestBody ArticlePostRequest request) {

        return ResponseEntity.ok(articleService.saveArticle(request));
    }

    @PostMapping(value = "upload/image/{article}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ImageDetails> uploadImage(@RequestPart MultipartFile image, @PathVariable String article) {
        return ResponseEntity.ok(articleService.uploadImage(image, article));
    }

    @GetMapping("status")
    public String getStatus() {
        return "Article controller is wokring!";
    }

    @PostMapping("add-comment")
    public ResponseEntity<String> addComment(@RequestBody CommentRequest request) {

        return ResponseEntity.ok(articleService.addComment(request));
    }

    @PostMapping("like/{article_id}")
    public ResponseEntity<String> likeArticle(@PathVariable("article_id") long id) {
        return ResponseEntity.ok(articleService.likeArticle(id));
    }

    @PostMapping("dislike/{article_id}")
    public ResponseEntity<String> dislikeArticle(@PathVariable("article_id") long id) {
        return ResponseEntity.ok(articleService.dislikeArticle(id));
    }

    @DeleteMapping("delete/{article_id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("article_id") long id) {
        return ResponseEntity.ok(articleService.deleteArticle(id));
    }

    @DeleteMapping("delete/image/{image_id}")
    public ResponseEntity<String> deleteArticleImage(@PathVariable("image_id") long id) {
        return ResponseEntity.ok(articleService.deleteArticleImage(id));
    }

    @PostMapping("update")
    public ResponseEntity<String> updatePost(@Valid @RequestBody ArticlePostRequest request) {
        return ResponseEntity.ok(articleService.updateArticle(request));
    }

    @GetMapping("get-one")
    public ResponseEntity<Article> getOneArticle(@Param("title") String title) {
        return ResponseEntity.ok(Article.toArticle(articleService.getOneArticle(title)));
    }

    @GetMapping("search/{keyword}")
    public ResponseEntity<?> searchByKeyword(@PathVariable String keyword, @Param("page") int page, @Param("size") int size) {
        return ResponseEntity.ok(ArticlePaginated.toArticle(articleService.searchByKeyword(keyword, page, size)));
    }

    @GetMapping("top-articles")
    public ResponseEntity<List<Article>> getTopArticles() {
        return ResponseEntity.ok(articleService.getTopArticles());
    }
}
