package Blog.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "articles")
@Entity
@Getter
@Setter
public class ArticleEntity extends TimeIntegration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String brief;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ImageEntity> images = new ArrayList<>();

    public void addImage(ImageEntity imageEntity) {
        images.add(imageEntity);
        imageEntity.setArticle(this);
    }

    @Min(0)
    @Max(5)
    private int stars;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthorEntity author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"article_id", "tag_id"}))
    private List<TagEntity> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int likes = 0;

    public void setContent(String content) {
        String temp = content.replaceAll("style=\"visibility: hidden;", "style=\"visibility: visible;");
        temp = temp.replaceAll("style=\"z-index: 2;", "style=\"z-index: -1; ");
        this.content = temp;
    }

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", category=" + category +
                ", image ids=" + images +
                '}';
    }
}
