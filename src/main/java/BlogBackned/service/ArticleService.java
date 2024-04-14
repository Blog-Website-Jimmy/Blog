package BlogBackned.service;

import BlogBackned.config.BeanNames;
import BlogBackned.entity.ArticleEntity;
import BlogBackned.entity.ImageEntity;
import BlogBackned.exception.ArticleWithThisTitleAlreadyExistsException;
import BlogBackned.exception.NoAuthorWithThisIdException;
import BlogBackned.exception.NoCategoryWithThisIdException;
import BlogBackned.exception.NoImageWithThisIdException;
import BlogBackned.helper.HelperFunctions;
import BlogBackned.model.ImageDetails;
import BlogBackned.repository.ArticleRepository;
import BlogBackned.repository.AuthorRepository;
import BlogBackned.repository.CategoryRepository;
import BlogBackned.repository.ImageRepository;
import BlogBackned.request.ArticlePostRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleService extends HelperFunctions {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final ImageRepository imageRepository;

    private final Path uploadImagePath;


    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository, ImageRepository imageRepository, @Qualifier(BeanNames.IMAGE_PATH_BEAN_NAME) Path uploadImagePath) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.imageRepository = imageRepository;
        this.uploadImagePath = uploadImagePath;
    }

    public Page<ArticleEntity> getAllArticles(int page,  int size) {
        Pageable pageable = PageRequest.of(page, size);
        var articles = articleRepository.findAll();

        return makingPagination(articles, pageable);
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
}
