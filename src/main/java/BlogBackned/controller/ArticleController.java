package BlogBackned.controller;

import BlogBackned.model.ArticlePaginated;
import BlogBackned.model.ImageDetails;
import BlogBackned.request.ArticlePostRequest;
import BlogBackned.request.CommentRequest;
import BlogBackned.service.ArticleService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
}
