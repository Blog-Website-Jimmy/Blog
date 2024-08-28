package Blog.controller;

import Blog.entity.AuthorEntity;
import Blog.model.Author;
import Blog.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<AuthorEntity> authors = authorService.getAuthors();
        return ResponseEntity.ok(authors.stream().map(Author::toAuthor).toList());
    }
}
