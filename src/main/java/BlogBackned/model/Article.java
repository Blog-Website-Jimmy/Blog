package BlogBackned.model;

import BlogBackned.entity.ArticleEntity;
import BlogBackned.entity.ImageEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Article {
    private long id;
    private String title;
    private String brief;
    private String content;
    private Author author;
    private List<Tag> tags;
    private Category category;
    private List<Comment> comments;
    private List<ImageEntity> images;
    private int stars;

    public static Article toArticle(ArticleEntity entity) {
        Article article = new Article();
        article.setId(entity.getId());
        article.setTitle(entity.getTitle());
        article.setBrief(entity.getBrief());
        article.setContent(entity.getContent());
        article.setStars(entity.getStars());
        article.setAuthor(Author.toAuthor(entity.getAuthor()));
        article.setTags(entity.getTags().stream().map(Tag::toTag).toList());
        article.setCategory(Category.toCategory(entity.getCategory()));
        article.setComments(entity.getComments().stream().map(Comment::toComment).toList());
        article.setImages(entity.getImages());
        return article;
    }
}
