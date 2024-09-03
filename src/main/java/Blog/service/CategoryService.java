package Blog.service;

import Blog.entity.ArticleEntity;
import Blog.entity.CategoryEntity;
import Blog.exception.CategoryWithThisNameAlreadyExists;
import Blog.exception.NoCategoryWithThisIdException;
import Blog.helper.HelperFunctions;
import Blog.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
public class CategoryService extends HelperFunctions {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> getAllCategories() {
        log.info("jimi is in blog");
        return categoryRepository.findAll().stream().filter(entity -> entity.getDeletedAt()==null).toList();
    }

    public Page<ArticleEntity> getArticlesByCategory(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page, size);
        var categoryEntity = categoryRepository.findByName(category);
        var articles = categoryEntity.get().getArticles();
        var notDeletedArticles = articles.stream().filter(artice -> artice.getDeletedAt() == null).toList();
        return makingPagination(notDeletedArticles, pageable);
    }

    public String updateCategory(long id, String name, int priority) {

        var categoryEntity = categoryRepository.findById(id).orElseThrow(NoCategoryWithThisIdException::new);
        categoryEntity.setName(name);
        categoryEntity.setPriority(priority);
        categoryRepository.save(categoryEntity);

        return "Category updated successfully!";
    }

    public String addCategory(String name, int order) {
        var category = categoryRepository.findByName(name);
        if (category.isPresent()) throw new CategoryWithThisNameAlreadyExists();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);
        categoryEntity.setPriority(order);
        categoryRepository.save(categoryEntity);
        return "Category was created!";
    }

    public String deleteCategory(long id) {
        var categoryEntity = categoryRepository.findById(id).orElseThrow(NoCategoryWithThisIdException::new);
        categoryEntity.setDeletedAt(OffsetDateTime.now());
        categoryRepository.save(categoryEntity);
        return "Category was deleted!";
    }
}
