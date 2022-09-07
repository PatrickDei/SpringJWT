package deisinger.demo.user;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Authentication registerUser(RegisterCommand registerCommand);
}
