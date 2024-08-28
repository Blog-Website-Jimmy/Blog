package Blog.model;

import Blog.entity.ArticleEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class ArticlePaginated {
    private List<Article> articles;
    private int currentPage;
    private int totalArticles;
    private int size;
    private int totalPages;

    public static ArticlePaginated toArticle(Page<ArticleEntity> articlePage) {
        ArticlePaginated article = new ArticlePaginated();
        article.setArticles(articlePage.getContent().stream().map(Article::toArticle).toList());
        article.setCurrentPage(articlePage.getNumber());
        article.setTotalArticles((int) articlePage.getTotalElements());
        article.setSize(articlePage.getSize());
        article.setTotalPages(articlePage.getTotalPages());
        return article;
    }
}
