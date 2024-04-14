package BlogBackned.service;

import BlogBackned.entity.AuthorEntity;
import BlogBackned.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorEntity> getAuthors() {
        return authorRepository.findAll();
    }
}
