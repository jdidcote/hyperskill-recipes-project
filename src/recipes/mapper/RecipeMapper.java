package recipes.mapper;

import recipes.model.Recipe;
import recipes.persistence.RecipeEntity;
import recipes.persistence.UserEntity;

public class RecipeMapper {

    public static Recipe fromEntity(RecipeEntity recipeEntity) {
        return Recipe.builder()
                .name(recipeEntity.getName())
                .description(recipeEntity.getDescription())
                .ingredients(recipeEntity.getIngredients())
                .directions(recipeEntity.getDirections())
                .category(recipeEntity.getCategory())
                .date(recipeEntity.getDate())
                .build();
    }

    public static RecipeEntity toEntity(Recipe recipe) {
        return RecipeEntity.builder()
                .name(recipe.getName())
                .description(recipe.getDescription())
                .ingredients(recipe.getIngredients())
                .directions(recipe.getDirections())
                .category(recipe.getCategory())
                .build();
    }

    public static RecipeEntity toEntity(Recipe recipe, UserEntity user) {
        return RecipeEntity.builder()
                .name(recipe.getName())
                .description(recipe.getDescription())
                .ingredients(recipe.getIngredients())
                .directions(recipe.getDirections())
                .category(recipe.getCategory())
                .user(user)
                .build();
    }
}
