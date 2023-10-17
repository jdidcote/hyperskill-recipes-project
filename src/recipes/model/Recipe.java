package recipes.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    private LocalDateTime date;
    @NotNull
    @NotEmpty
    private List<String> ingredients;
    @NotNull
    @NotEmpty
    private List<String> directions;
}
