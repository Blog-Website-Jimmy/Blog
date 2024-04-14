package BlogBackned.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class ArticleGetRequest {
    private Collection<Integer> tagID;
    private String search;
    private int page;
    private int size;
}
