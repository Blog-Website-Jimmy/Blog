package BlogBackned.model;

import BlogBackned.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Comment {
    private long id;
    private String author;
    private String comment;

    public static Comment toComment(CommentEntity entity) {
        Comment comment = new Comment();
        comment.setId(entity.getId());
        comment.setComment(entity.getComment());
        comment.setAuthor(entity.getAuthor());
        return comment;
    }
}
