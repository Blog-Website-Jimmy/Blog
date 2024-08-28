package Blog.service;

import Blog.config.BeanNames;
import Blog.entity.ArticleEntity;
import Blog.entity.CommentEntity;
import Blog.entity.ImageEntity;
import Blog.exception.*;
import Blog.helper.HelperFunctions;
import Blog.model.Article;
import Blog.model.ImageDetails;
import Blog.repository.*;
import Blog.request.ArticlePostRequest;
import Blog.request.CommentRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleService extends HelperFunctions {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    private final Path uploadImagePath;


    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository, ImageRepository imageRepository, CommentRepository commentRepository, @Qualifier(BeanNames.IMAGE_PATH_BEAN_NAME) Path uploadImagePath) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.imageRepository = imageRepository;
        this.commentRepository = commentRepository;
        this.uploadImagePath = uploadImagePath;
    }

    public Page<ArticleEntity> getAllArticles(int page,  int size) {
        Pageable pageable = PageRequest.of(page, size);
        var articles = articleRepository.findAll();
        var notDeletedArticles = articles.stream().filter(entity -> entity.getDeletedAt() == null).toList();

        return makingPagination(notDeletedArticles, pageable);
    }

    public String saveArticle(ArticlePostRequest request) {
        var article = articleRepository.findByTitle(request.getTitle());
        if (article.isPresent()) throw new ArticleWithThisTitleAlreadyExistsException();
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow(NoCategoryWithThisIdException::new);
        var author = authorRepository.findById(request.getAuthorId()).orElseThrow(NoAuthorWithThisIdException::new);
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(request.getTitle());
        articleEntity.setBrief(request.getBrief());
        articleEntity.setContent(request.getContent());
        articleEntity.setAuthor(author);
        articleEntity.setCategory(category);
        articleEntity.setImages(getImagesFromIds(request.getImageIds()));
        System.out.println(articleEntity);
        articleRepository.save(articleEntity);
        return "Article was saved successfully!";
    }

    public ImageDetails uploadImage(MultipartFile image, String article) {
        try {
            var modifiedArticle = replaceSpaceWithUnderScore(article);
            var path = moveImgFile(image, modifiedArticle);
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setName(removeImageExtension(Objects.requireNonNull(image.getOriginalFilename())));
            String pathString = path.toString();
            imageEntity.setPathOrURL(pathString.substring(1));
            var result = imageRepository.save(imageEntity);
            return new ImageDetails(result.getId(), pathString.substring(1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Path moveImgFile(MultipartFile multipartFile, String article) throws Exception {
        var uploadedFileLocation = uploadImagePath.resolve(article+"/"+multipartFile.getOriginalFilename());
        File uploadFile = new File("Images/"+article+"/");
        uploadFile.mkdir();
        multipartFile.transferTo(uploadedFileLocation);
        return uploadedFileLocation;
    }

    @Transactional
    public List<ImageEntity> getImagesFromIds(List<Long> ids) {
        List<ImageEntity> images = new ArrayList<>();
        ids.forEach(id->{
            var image = imageRepository.findById(id).orElseThrow(NoImageWithThisIdException::new);
            images.add(image);
        });
        return images;
    }

    public String addComment(CommentRequest request) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAuthor(request.getAuthor());
        commentEntity.setComment(request.getComment());
        commentEntity.setArticle(articleRepository.findById(request.getArticleId()).get());
        commentRepository.save(commentEntity);
        return "Comment was added successfully!";
    }

    public String likeArticle(long id) {
        var articleEntity = articleRepository.findById(id).get();
        var likeCounts = articleEntity.getLikes();
        articleEntity.setLikes(likeCounts + 1);
        articleRepository.save(articleEntity);
        return "Article was liked!";
    }

    public String dislikeArticle(long id) {
        var articleEntity = articleRepository.findById(id).orElseThrow(NoArticleWithThisIdException::new);
        var likeCounts = articleEntity.getLikes();
        articleEntity.setLikes(likeCounts - 1);
        articleRepository.save(articleEntity);
        return "Article was disliked!";
    }

    public String deleteArticle(long id) {
        var articleEntity = articleRepository.findById(id).orElseThrow(NoArticleWithThisIdException::new);
        articleEntity.setDeletedAt(OffsetDateTime.now());
        articleRepository.save(articleEntity);
        return "Article was deleted!";
    }

    @Transactional
    public String deleteArticleImage(long id) {
        var articlesByImageId = articleRepository.findByImageId(id);
        var imageEntity = imageRepository.getReferenceById(id);
        articlesByImageId.stream().forEach(articleEntity->{
           articleEntity.getImages().remove(imageEntity);
       });
        articleRepository.saveAllAndFlush(articlesByImageId);
        return "Image was deleted!";
    }

    public String updateArticle(ArticlePostRequest request) {
        var articleToUpdate = articleRepository.findByTitle(request.getTitle()).orElseThrow(NoArticleWithThisTitleException::new);
        articleToUpdate.setAuthor(authorRepository.findById(request.getAuthorId()).orElseThrow(NoAuthorWithThisIdException::new));
        articleToUpdate.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(NoCategoryWithThisIdException::new));
        articleToUpdate.setContent(request.getContent());
        articleToUpdate.setBrief(request.getBrief());
        var images = articleToUpdate.getImages();
        images.addAll(getImagesFromIds(request.getImageIds()));
        articleToUpdate.setImages(images);
        articleRepository.save(articleToUpdate);
        return "Article was updated!";

    }

    public ArticleEntity getOneArticle(String title) {
        return articleRepository.findByTitle(title).orElseThrow(NoArticleWithThisTitleException::new);
    }

    public Page<ArticleEntity> searchByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ArticleEntity> resultArticles ;
        if (keyword.isBlank() || keyword.isEmpty()) {
            resultArticles = articleRepository.findAll();
        } else {
             resultArticles = articleRepository.findByKeyword(keyword);
        }
        var notDeletedArticles = resultArticles.stream().filter(entity -> entity.getDeletedAt() == null).toList();

        return makingPagination(notDeletedArticles, pageable);
    }

    public List<Article> getTopArticles() {
        var topThree = articleRepository.findTopThree();
        List<Article> articles = new ArrayList<>();
        topThree.forEach(entity -> articles.add(Article.toArticle(entity)));
        return articles;
    }

}
