package recipes.repository;

import org.h2.engine.User;
import org.springframework.data.jpa.repository.JpaRepository;
import recipes.persistence.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAll();
}
