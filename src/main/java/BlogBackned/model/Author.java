package BlogBackned.model;

import BlogBackned.entity.AuthorEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {
    private long id;
    private String name;

    public static Author toAuthor(AuthorEntity entity) {
        Author author = new Author();
        author.setId(entity.getId());
        author.setName(entity.getName());
        return author;
    }
}
