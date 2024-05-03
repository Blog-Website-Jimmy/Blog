package BlogBackned.repository;

import BlogBackned.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByTitle(String title);

    @Query(value = "SELECT * FROM articles JOIN article_images ON articles.id=article_images.article_id WHERE article_images.image_id=?1",nativeQuery = true)
    List<ArticleEntity> findByImageId(Long id);
}
