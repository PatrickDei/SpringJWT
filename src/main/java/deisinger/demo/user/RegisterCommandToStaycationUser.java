package deisinger.demo.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class RegisterCommandToStaycationUser implements Converter<RegisterCommand, StaycationUser> {

    private final PasswordEncoder passwordEncoder;

    public RegisterCommandToStaycationUser(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StaycationUser convert(RegisterCommand value) {
        StaycationUser user = new StaycationUser();
        user.setFirstname(value.getFirstname());
        user.setLastname(value.getLastname());
        user.setPassword(passwordEncoder.encode(value.getPassword()));
        user.setUsername(value.getEmail());
        return user;
    }
}
