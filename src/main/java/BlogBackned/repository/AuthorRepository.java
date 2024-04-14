package BlogBackned.repository;

import BlogBackned.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
}
