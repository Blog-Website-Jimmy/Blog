package BlogBackned.controller;

import BlogBackned.model.ArticlePaginated;
import BlogBackned.model.ImageDetails;
import BlogBackned.request.ArticlePostRequest;
import BlogBackned.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("article")
@CrossOrigin
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
}
