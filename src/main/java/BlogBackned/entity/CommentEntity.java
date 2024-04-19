package BlogBackned.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class CommentEntity extends TimeIntegration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String author;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;



}
