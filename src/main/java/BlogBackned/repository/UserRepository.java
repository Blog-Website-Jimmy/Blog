package BlogBackned.repository;

import BlogBackned.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByIdAndToken(long id,String token);

    Optional<UserEntity> findByUsernameAndPassword(String username, String token);

}
