package recipes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import recipes.exception.InvalidNumArgumentsException;
import recipes.exception.UnauthorizedUserException;
import recipes.model.Recipe;
import recipes.model.RecipeId;
import recipes.persistence.UserEntity;
import recipes.service.UserService;
import recipes.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipesService recipesService;
    private final UserServiceImpl userService;

    @PostMapping("new")
    RecipeId add(@Valid @RequestBody Recipe recipe) {
        UserEntity userEntity = userService.getUserByEmail(getLoggedInUsername());
        return recipesService.addRecipe(recipe, userEntity);
    }

    @GetMapping("{id}")
    Recipe findRecipeById(@PathVariable Long id) {
        return recipesService.findRecipeById(id);
    }

    @GetMapping("search")
    List<Recipe> searchRecipes(@RequestParam(required = false) String category,
                               @RequestParam(required = false) String name) throws InvalidNumArgumentsException{

        boolean categoryPresent = category != null && !category.isBlank();
        boolean namePresent = name != null && !name.isBlank();

        if (categoryPresent == namePresent) {
            throw new InvalidNumArgumentsException("Only one search param can be provided");
        }
        return categoryPresent ? recipesService.searchByCategory(category) : recipesService.searchByName(name);
    }

    @PutMapping("{id}")
    ResponseEntity<Void> updateRecipeById(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {

        validateRecipeOwner(id);

        recipesService.updateRecipeById(id, recipe);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) {
        validateRecipeOwner(id);

        recipesService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private void validateRecipeOwner(Long recipeId) throws UnauthorizedUserException {
        UserEntity recipeOwner = recipesService.getRecipeOwner(recipeId);

        if (!recipeOwner.getEmail().equals(getLoggedInUsername())) {
            throw new UnauthorizedUserException("Not the owner of this recipe");
        }

        }

}
