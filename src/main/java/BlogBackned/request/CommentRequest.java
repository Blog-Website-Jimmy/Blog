package BlogBackned.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String author;
    private String comment;
    private long articleId;
}
