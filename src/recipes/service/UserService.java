package recipes.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import recipes.model.UserRegistrationRequest;

public interface UserService extends UserDetailsService {
    void saveUser(UserRegistrationRequest registrationRequest);
    boolean emailExists(String email);

}
