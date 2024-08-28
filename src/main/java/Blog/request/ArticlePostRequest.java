package Blog.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticlePostRequest {
    @NotBlank(message = "Content cant be empty!")
    private String content;
    private long categoryId;
    @NotBlank(message = "Title cant be empty!")
    private String title;
    private long authorId;
    @NotBlank(message = "Brief cant be empty!")
    private String brief;
    private List<Long> imageIds;
}
