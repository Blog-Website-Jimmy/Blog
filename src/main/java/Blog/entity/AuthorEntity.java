package Blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "authors")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<ArticleEntity> articles;

    @OneToMany(mappedBy = "author")
    private List<CommentEntity> comments;

}
