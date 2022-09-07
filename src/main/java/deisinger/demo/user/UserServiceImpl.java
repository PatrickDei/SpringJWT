package deisinger.demo.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RegisterCommandToStaycationUser registerCommandToStaycationUser;

    public UserServiceImpl(UserRepository userRepository, RegisterCommandToStaycationUser registerCommandToStaycationUser) {
        this.userRepository = userRepository;
        this.registerCommandToStaycationUser = registerCommandToStaycationUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find the user with username: " + username));
    }

    @Override
    public Authentication registerUser(RegisterCommand registerCommand) {
        StaycationUser user = registerCommandToStaycationUser.convert(registerCommand);
        if (user != null) {
            userRepository.save(user);

            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        }
        else {
            throw new RuntimeException();
        }
    }
}