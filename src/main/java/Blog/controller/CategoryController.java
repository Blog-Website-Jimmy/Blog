package Blog.controller;

import Blog.model.ArticlePaginated;
import Blog.model.Category;
import Blog.request.CategoryRequest;
import Blog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
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
    public ResponseEntity<?> getArticleByOneCategory(@RequestParam int page, @RequestParam int size, @RequestParam String category) {
        return ResponseEntity.ok(ArticlePaginated.toArticle(categoryService.getArticlesByCategory(page, size, category)));
    }

    @PostMapping("update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable("id") long id, @RequestBody CategoryRequest request) {
        System.out.println("category is => " + request);
        return ResponseEntity.ok(categoryService.updateCategory(id, request.getName(), request.getPriority()));
    }

    @GetMapping("status")
    public String getStatus() {
        return "Category controller is working fine and healthy!";
    }

    @PostMapping("add")
    public ResponseEntity<String> addCategory(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.addCategory(request.getName(), request.getPriority()));
    }
    @DeleteMapping("delete/{category_id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("category_id") long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
