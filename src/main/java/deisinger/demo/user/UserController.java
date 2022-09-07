package deisinger.demo.user;

import deisinger.demo.security.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public UserController(UserService userService, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/data")
    public ResponseEntity<UserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails securityUser = userService.loadUserByUsername(authentication.getName());

        return ResponseEntity.ok(securityUser);
    }

    @PostMapping("/register")
    public ResponseEntity<Token> register(@Valid @RequestBody RegisterCommand registerCommand) {
        Authentication authentication = userService.registerUser(registerCommand);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new Token(jwt));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody LoginCommand loginCommand) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginCommand.getUsername(), loginCommand.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new Token(jwt));
    }

    record Token(String token) {}
}
