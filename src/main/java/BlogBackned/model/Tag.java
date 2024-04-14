package BlogBackned.model;

import BlogBackned.entity.TagEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {
    private long id;
    private String name;

    public static Tag toTag(TagEntity entity) {
        Tag tag = new Tag();
        tag.setId(entity.getId());
        tag.setName(entity.getName());
        return tag;
    }
}
