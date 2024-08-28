package Blog.model;

import Blog.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private long id;
    private String name;
    private int priority;

    public static Category toCategory(CategoryEntity entity) {
        Category category = new Category();
        category.setId(entity.getId());
        category.setName(entity.getName());
        if (entity.getPriority() == null) {
            category.setPriority(0);
        } else {
            category.setPriority(entity.getPriority());
        }
        return category;
    }
}
