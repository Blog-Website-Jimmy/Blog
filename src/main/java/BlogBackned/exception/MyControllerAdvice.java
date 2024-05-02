package BlogBackned.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundError(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoCategoryWithThisIdException.class)
    public ResponseEntity<String> categoryNotFound(NoCategoryWithThisIdException noCategoryWithThisIdException) {
        return new ResponseEntity<>("Category not found!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ArticleWithThisTitleAlreadyExistsException.class)
    public ResponseEntity<String> articleAlreadyExists(ArticleWithThisTitleAlreadyExistsException articleWithThisTitleAlreadyExistsException) {
        return new ResponseEntity<>("Article with this title already exists!", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoAuthorWithThisIdException.class)
    public ResponseEntity<String> authorNotFund(NoAuthorWithThisIdException noAuthorWithThisIdException) {
        return new ResponseEntity<>("Author not found!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoImageWithThisIdException.class)
    public ResponseEntity<String> imageNotFound(NoImageWithThisIdException noImageWithThisIdException) {
        return new ResponseEntity<>("Image not found!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoArticleWithThisIdException.class)
    public ResponseEntity<String> articleNotFound(NoArticleWithThisIdException noArticleWithThisIdException) {
        return new ResponseEntity<>("Article not found!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryWithThisNameAlreadyExists.class)
    public ResponseEntity<String> categoryAlreadyExists(CategoryWithThisNameAlreadyExists categoryWithThisNameAlreadyExists) {
        return new ResponseEntity<>("Category with this name already exists!", HttpStatus.BAD_REQUEST);
    }


    //    ALWAYS KEEP THIS METHOD AT THE BOTTOM
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
