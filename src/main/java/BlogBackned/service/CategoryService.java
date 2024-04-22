package BlogBackned.service;

import BlogBackned.entity.ArticleEntity;
import BlogBackned.entity.CategoryEntity;
import BlogBackned.exception.NoCategoryWithThisIdException;
import BlogBackned.helper.HelperFunctions;
import BlogBackned.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends HelperFunctions {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Page<ArticleEntity> getArticlesByCategory(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page, size);
        var categoryEntity = categoryRepository.findByName(category);
        var articles = categoryEntity.get().getArticles();
        var notDeletedArticles = articles.stream().filter(artice -> artice.getDeletedAt() == null).toList();
        return makingPagination(notDeletedArticles, pageable);
    }

    public String updateCategoey(long id, String name) {

        var categoryEntity = categoryRepository.findById(id).orElseThrow(NoCategoryWithThisIdException::new);
        categoryEntity.setName(name);
        categoryRepository.save(categoryEntity);

        return "Category updated successfully!";
    }
}
