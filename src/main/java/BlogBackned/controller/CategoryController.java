package BlogBackned.controller;

import BlogBackned.model.ArticlePaginated;
import BlogBackned.model.Category;
import BlogBackned.service.CategoryService;
import jakarta.websocket.server.PathParam;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllCategories() {
        var categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories.stream().map(Category::toCategory).toList());
    }

    @GetMapping("articles")
    public ResponseEntity<?> getArticleByOneCategory(@RequestParam int page, @RequestParam int size,@RequestParam String category) {
        return ResponseEntity.ok(ArticlePaginated.toArticle(categoryService.getArticlesByCategory(page, size, category)));
    }
}
