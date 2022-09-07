package deisinger.demo.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser implements UserDetails {

    private final StaycationUser staycationUser;

    public SecurityUser(StaycationUser staycationUser) {
        this.staycationUser = staycationUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return staycationUser.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return staycationUser.getPassword();
    }

    @Override
    public String getUsername() {
        return staycationUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
