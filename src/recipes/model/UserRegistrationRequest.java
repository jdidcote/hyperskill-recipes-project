package recipes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserRegistrationRequest {
    @Pattern(regexp = ".+@.+\\..+")
    String email;
    @NotBlank
    @Size(min=8)
    String password;
}
