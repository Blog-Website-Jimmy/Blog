package BlogBackned.helper;

import BlogBackned.config.BeanNames;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;

public class HelperFunctions {
    @Value("${auth.salt}")
    private String AUTH_SALT;

    public <T> Page<T> makingPagination(List<T> givenList, Pageable pageable) {
        if (pageable.getOffset() > givenList.size()) {
            long total = 0L;
            return new PageImpl<>(List.of(), pageable, total);
        }
        if ((pageable.getOffset() <= givenList.size()) && (pageable.getOffset() + pageable.getPageSize() > givenList.size())) {
            var size = givenList.size();
            var givenSubList = givenList.subList((int) pageable.getOffset(), size);
            return new PageImpl<>(givenSubList, pageable, givenList.size());
        }
        var givenSubList = givenList.subList((int) pageable.getOffset(), (int) (pageable.getOffset() + pageable.getPageSize()));
        return new PageImpl<>(givenSubList, pageable, givenList.size());
    }

    public String hashPassword(String rawPassword) {
        var spec = new PBEKeySpec(rawPassword.toCharArray(), AUTH_SALT.getBytes(), 65536, 512);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkPasswordComplexity(String rawPassword) {


        return rawPassword.length() > 8 && // check password length
                containsNumber(rawPassword) && // check password has number or digit
                containsLowerCase(rawPassword) && // check password has lower case letter
                containsUpperCase(rawPassword); // check password has upper case letter
    }

    public boolean checkEmailComplexity(String email) {
        return email.contains("@");
    }


    public boolean containsLowerCase(String value) {
        return contains(value, ch -> Character.isLetter(ch) && Character.isLowerCase(ch));
    }

    public boolean containsUpperCase(String value) {
        return contains(value, ch -> Character.isLetter(ch) && Character.isUpperCase(ch));
    }

    public boolean containsNumber(String value) {
        return contains(value, Character::isDigit);
    }

    public boolean contains(String value, IntPredicate predicate) {
        return value.chars().anyMatch(predicate);
    }

    public String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] token = new byte[32];
        random.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }

    public String replaceSpaceWithUnderScore(String given) {
        return given.replace(" ", "_");
    }

    public String removeImageExtension(String given) {
        var pointIndex = 0;
        for (int i = given.length()-1; i >  0 ; i--) {
            if (given.charAt(i) == '.') {
                pointIndex = i;
            }
        }
        return given.substring(0, pointIndex);
    }


}
