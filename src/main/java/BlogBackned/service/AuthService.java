package BlogBackned.service;

import BlogBackned.entity.UserEntity;
import BlogBackned.exception.UserNotFoundException;
import BlogBackned.helper.HelperFunctions;
import BlogBackned.repository.UserRepository;
import BlogBackned.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService extends HelperFunctions {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticate(UserRequest userRequest) {
        var hashedPassword = hashPassword(userRequest.getPassword());
        UserEntity userEntity = userRepository.findByUsernameAndPassword(userRequest.getUsername(), hashedPassword).orElseThrow(UserNotFoundException::new);
        userEntity.setToken(generateToken());
        userRepository.save(userEntity);
        return userEntity.getToken();
    }

    public void validateToken(Long id, String tokenValue) {
        log.info(tokenValue);
        var user = userRepository.findByIdAndToken(id, tokenValue);
        if (user.isEmpty()) {
            throw new RuntimeException("No token like this, maybe someone is trying to hack?!");
        }
    }
}
