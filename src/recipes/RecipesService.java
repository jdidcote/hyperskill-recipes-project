package recipes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.exception.IdNotFoundException;
import recipes.mapper.RecipeMapper;
import recipes.model.Recipe;
import recipes.model.RecipeId;
import recipes.persistence.RecipeEntity;
import recipes.persistence.UserEntity;
import recipes.repository.RecipeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipesService {

    private final RecipeRepository recipeRepository;

    @Transactional
    public RecipeId addRecipe(Recipe recipe, UserEntity user) {

        RecipeEntity recipeEntity = RecipeMapper.toEntity(recipe, user);
        RecipeEntity savedRecipe = recipeRepository.save(recipeEntity);

        return new RecipeId(savedRecipe.getId());
    }

    @Transactional(readOnly = true)
    public Recipe findRecipeById(Long id) {
        RecipeEntity foundRecipe = validateIdAndFindRecipe(id);
        return RecipeMapper.fromEntity(foundRecipe);
    }

    @Transactional(readOnly = true)
    public UserEntity getRecipeOwner(Long id) {
        RecipeEntity foundRecipe = validateIdAndFindRecipe(id);
        return foundRecipe.getUser();
    }

    @Transactional
    public void updateRecipeById(Long id, Recipe recipe) {
        RecipeEntity foundRecipe = validateIdAndFindRecipe(id);

        foundRecipe.setName(recipe.getName());
        foundRecipe.setDescription(recipe.getDescription());
        foundRecipe.setCategory(recipe.getCategory());
        foundRecipe.setIngredients(recipe.getIngredients());
        foundRecipe.setDirections(recipe.getDirections());

        recipeRepository.save(foundRecipe);
    }

    @Transactional
    public void deleteRecipeById(Long id) {
        RecipeEntity foundRecipe = validateIdAndFindRecipe(id);
        recipeRepository.delete(foundRecipe);
    }

    private RecipeEntity validateIdAndFindRecipe(Long id) throws IdNotFoundException, IllegalArgumentException{
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Optional<RecipeEntity> optionalRecipeEntity = recipeRepository.findById(id);
        if (optionalRecipeEntity.isEmpty()) {
            String msg = String.format("Recipe with id %s not found", id);
            throw new IdNotFoundException(msg);
        }
        return optionalRecipeEntity.get();
    }

    public List<Recipe> searchByCategory(String category) {
        List<RecipeEntity> foundRecipeEntities = recipeRepository.findAllByCategoryIgnoreCase(category);
        List<Recipe> foundRecipes = foundRecipeEntities.stream().map(RecipeMapper::fromEntity).toList();
        return sortRecipesByDateDesc(foundRecipes);
    }

    public List<Recipe> searchByName(String name) {
        List<RecipeEntity> foundRecipeEntities = recipeRepository.findAllByNameContainingIgnoreCase(name);
        List<Recipe> foundRecipes = foundRecipeEntities.stream().map(RecipeMapper::fromEntity).toList();
        return sortRecipesByDateDesc(foundRecipes);
    }

    private List<Recipe> sortRecipesByDateDesc(List<Recipe> recipes) {
        Comparator<Recipe> compareByDate = Comparator.comparing(Recipe::getDate).reversed();
        return recipes.stream()
                .sorted(compareByDate)
                .collect(Collectors.toList());
    }

}
