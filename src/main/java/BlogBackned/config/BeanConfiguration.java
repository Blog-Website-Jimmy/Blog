package BlogBackned.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class BeanConfiguration {


    @Bean(BeanNames.IMAGE_PATH_BEAN_NAME)
    public Path imagePath(@Value(PropertyNames.IMAGE_PATH_PROP_NAME) String imagePath) {
        var path = Path.of(imagePath);
        if (!path.toFile().exists())
            throw new RuntimeException("Given directory does not exists : " + imagePath);
        return path;
    }

}
