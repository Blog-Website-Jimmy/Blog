package BlogBackned.model;

import BlogBackned.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private long id;
    private String name;

    public static Category toCategory(CategoryEntity entity) {
        Category category = new Category();
        category.setId(entity.getId());
        category.setName(entity.getName());
        return category;
    }
}
