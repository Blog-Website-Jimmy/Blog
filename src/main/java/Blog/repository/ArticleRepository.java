package Blog.repository;

import Blog.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByTitle(String title);

    @Query(value = "SELECT * FROM articles JOIN article_images ON articles.id=article_images.article_id WHERE article_images.image_id=?1",nativeQuery = true)
    List<ArticleEntity> findByImageId(Long id);

    @Query(value = "SELECT * FROM articles a WHERE lower(a.title) LIKE %:keyword% OR lower(a.brief) LIKE %:keyword%", nativeQuery = true)
    List<ArticleEntity> findByKeyword(String keyword);

    @Query(value = "SELECT * FROM articles WHERE deleted_at IS NULL ORDER BY likes DESC LIMIT 3", nativeQuery = true)
    List<ArticleEntity> findTopThree();

}
