package BlogBackned.config;

import BlogBackned.entity.UserEntity;
import BlogBackned.helper.HelperFunctions;
import BlogBackned.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class DataInitializer extends HelperFunctions implements ApplicationRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    Running this codes here because cant create user with hashed password in data.sql
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByUsername("admin@mail.com").isPresent() ) return;
        UserEntity admin = new UserEntity();
        admin.setName("admin");
        admin.setUsername("admin@mail.com");
        admin.setPassword(hashPassword("admin"));
        admin.setCreatedAt(OffsetDateTime.now());
        userRepository.save(admin);
    }
}
