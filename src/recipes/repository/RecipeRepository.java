package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.persistence.RecipeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeEntity, Long> {
    Optional<RecipeEntity> findById(Long id);

    List<RecipeEntity> findAllByCategoryIgnoreCase(String category);

    List<RecipeEntity> findAllByNameContainingIgnoreCase(String name);
}